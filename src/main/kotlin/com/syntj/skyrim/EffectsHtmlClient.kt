package com.syntj.skyrim

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream
import kotlin.math.min

class EffectsHtmlClient(private val effectsUrl: String = EFFECTS_URL) {

    companion object {
        const val EFFECTS_URL = "https://www.powtions.com/effects"
        const val DEFAULT_RESOURCE_PATH = "powtions/effects.html"
        val logger: Logger = LoggerFactory.getLogger(EffectsHtmlClient::class.java)
        private val mapper = jacksonObjectMapper()
    }

    /**
     * Fetches or loads the effects document, and renders the HTML table
     * sourced by the JavaScript files if not already present.
     */
    fun fetchEffectsDocument(): Document {
        logger.info("Fetching effects page from $effectsUrl")
        val document: Document = try {
            if (effectsUrl.startsWith("http://") || effectsUrl.startsWith("https://")) {
                try {
                    Jsoup.connect(effectsUrl).get()
                } catch (e: Exception) {
                    logger.warn("Could not fetch remote URL $effectsUrl, falling back to local resource", e)
                    loadLocalEffectsDocument()
                }
            } else {
                loadLocalEffectsDocument(effectsUrl)
            }
        } catch (e: Exception) {
            logger.warn("Failed to load document from $effectsUrl, attempting default local resource", e)
            loadLocalEffectsDocument()
        }

        renderTableIfMissing(document)
        return document
    }

    /**
     * Renders `src/main/resources/powtions/effects.html`'s HTML table and returns the contents
     * as a list of [EffectData] objects.
     */
    fun fetchEffects(): List<EffectData> {
        val document = fetchEffectsDocument()
        val effectsFromDoc = extractEffectsFromDocument(document)
        if (effectsFromDoc.isNotEmpty()) {
            return effectsFromDoc
        }
        val rows = fetchEffectsTableRowsFromDocument(document)
        return parseEffectsFromRows(rows)
    }

    /**
     * Alias for [fetchEffects] to return the contents as a list of [EffectData] objects.
     */
    fun getEffects(): List<EffectData> = fetchEffects()

    /**
     * Fetches the effects page and returns the rendered table found on it as a
     * list of rows, where each row is a list of the trimmed text content of
     * its cells (both header `<th>` and data `<td>` cells).
     */
    fun fetchEffectsTableRows(): List<List<String>> {
        val document = fetchEffectsDocument()
        return fetchEffectsTableRowsFromDocument(document)
    }

    private fun fetchEffectsTableRowsFromDocument(document: Document): List<List<String>> {
        val table = document.selectFirst("table")
            ?: throw IllegalStateException("No table found on page at $effectsUrl")

        return table.select("tr").map { row: Element ->
            row.select("th, td").map { cell -> cell.text().trim() }
        }
    }

    private fun loadLocalEffectsDocument(path: String = DEFAULT_RESOURCE_PATH): Document {
        val htmlContent = loadResourceOrFile(path)
        return Jsoup.parse(htmlContent)
    }

    private fun loadResourceOrFile(path: String): String {
        val cleanPath = path.removePrefix("/")
        val stream: InputStream? = EffectsHtmlClient::class.java.getResourceAsStream("/$cleanPath")
            ?: Thread.currentThread().contextClassLoader.getResourceAsStream(cleanPath)

        if (stream != null) {
            return stream.bufferedReader().use { it.readText() }
        }

        val file = File(path)
        if (file.exists()) {
            return file.readText()
        }

        val srcFile = File("src/main/resources", cleanPath)
        if (srcFile.exists()) {
            return srcFile.readText()
        }

        throw java.io.FileNotFoundException("Could not find resource or file: $path")
    }

    private fun renderTableIfMissing(document: Document) {
        if (document.selectFirst("table") != null) {
            return
        }

        val effects = extractEffectsFromDocument(document)
        if (effects.isEmpty()) {
            logger.warn("No effects found in sourced JavaScript files")
            return
        }

        var root = document.selectFirst("#root")
        if (root == null) {
            root = document.body().appendElement("div").attr("id", "root")
        }

        val tableHtml = StringBuilder()
        tableHtml.append("<table>")
        tableHtml.append("<caption>All Effects</caption>")
        tableHtml.append("<thead><tr>")
        tableHtml.append("<th>Name</th>")
        tableHtml.append("<th>Value</th>")
        tableHtml.append("<th>Base cost</th>")
        tableHtml.append("<th>Base magnitude</th>")
        tableHtml.append("<th>Base duration</th>")
        tableHtml.append("</tr></thead>")
        tableHtml.append("<tbody>")

        for (effect in effects) {
            tableHtml.append("<tr>")
            tableHtml.append("<td>").append(escapeHtml(effect.name)).append("</td>")
            tableHtml.append("<td>").append(effect.value).append("</td>")
            tableHtml.append("<td>").append(effect.baseCost).append("</td>")
            tableHtml.append("<td>").append(effect.baseMagnitude).append("</td>")
            tableHtml.append("<td>").append(effect.baseDuration).append("</td>")
            tableHtml.append("</tr>")
        }

        tableHtml.append("</tbody></table>")
        root.html(tableHtml.toString())
    }

    private fun extractEffectsFromDocument(document: Document): List<EffectData> {
        val scriptElements = document.select("script[src]")
        val scriptFiles = scriptElements.mapNotNull { elem ->
            val src = elem.attr("src")
            if (src.contains("googletagmanager.com") || src.startsWith("http://") || src.startsWith("https://")) {
                null
            } else {
                val filename = src.substringAfterLast("/").substringBefore("?")
                if (filename.endsWith(".js")) filename else null
            }
        }

        val allJsFiles = if (scriptFiles.isNotEmpty()) scriptFiles else listOf("2.cef0ba1f.chunk.js", "main.15fe5d94.chunk.js")

        for (jsFileName in allJsFiles) {
            try {
                val jsContent = loadResourceOrFile("powtions/$jsFileName")
                val effects = parseEffectsFromJs(jsContent)
                if (effects.isNotEmpty()) {
                    return effects
                }
            } catch (e: Exception) {
                logger.debug("Failed to parse JS file $jsFileName", e)
            }
        }
        return emptyList()
    }

    private fun parseEffectsFromJs(jsContent: String): List<EffectData> {
        val vMatch = Regex("V\\s*=\\s*\\[\\s*\\{\\s*name\\s*:").find(jsContent)
        val keyIndex = if (vMatch != null) {
            jsContent.indexOf("[", vMatch.range.first)
        } else {
            val idx = jsContent.indexOf("[{ name:")
            if (idx != -1 && jsContent.substring(idx, min(idx + 500, jsContent.length)).contains("valueAt100Skill")) idx else -1
        }

        if (keyIndex == -1) {
            return emptyList()
        }

        var depth = 0
        var endIndex = -1
        for (i in keyIndex until jsContent.length) {
            if (jsContent[i] == '[') depth++
            else if (jsContent[i] == ']') {
                depth--
                if (depth == 0) {
                    endIndex = i + 1
                    break
                }
            }
        }

        if (endIndex == -1) {
            return emptyList()
        }

        val rawArrayStr = jsContent.substring(keyIndex, endIndex)

        var jsonStr = rawArrayStr.replace(Regex("([{,])\\s*([a-zA-Z0-9_]+)\\s*:"), "$1\"$2\":")
        jsonStr = jsonStr.replace(Regex(":\\s*\\.([0-9]+)"), ": 0.$1")

        return try {
            val jsonNode = mapper.readTree(jsonStr)
            jsonNode.mapNotNull { node ->
                try {
                    val name = node.get("name")?.asText() ?: return@mapNotNull null
                    val valueNode = node.get("valueAt100Skill") ?: return@mapNotNull null
                    val costNode = node.get("baseCost") ?: return@mapNotNull null
                    val magNode = node.get("magnitude") ?: return@mapNotNull null
                    val durNode = node.get("duration") ?: return@mapNotNull null

                    EffectData(
                        name = name,
                        value = valueNode.asDouble(),
                        baseCost = costNode.asDouble(),
                        baseMagnitude = magNode.asDouble(),
                        baseDuration = durNode.asDouble()
                    )
                } catch (e: Exception) {
                    logger.warn("Skipping unparseable effect node: $node", e)
                    null
                }
            }
        } catch (e: Exception) {
            logger.warn("Failed to parse converted JSON string: $jsonStr", e)
            emptyList()
        }
    }

    private fun parseEffectsFromRows(rows: List<List<String>>): List<EffectData> {
        if (rows.size <= 1) return emptyList()
        val header = rows.first()
        val nameIdx = header.indexOfFirst { it.equals("Name", ignoreCase = true) }
        val valueIdx = header.indexOfFirst { it.equals("Value", ignoreCase = true) }
        val costIdx = header.indexOfFirst { it.equals("Base cost", ignoreCase = true) }
        val magnitudeIdx = header.indexOfFirst { it.equals("Base magnitude", ignoreCase = true) }
        val durationIdx = header.indexOfFirst { it.equals("Base duration", ignoreCase = true) }

        if (listOf(nameIdx, valueIdx, costIdx, magnitudeIdx, durationIdx).any { it < 0 }) {
            return emptyList()
        }

        return rows.drop(1).mapNotNull { row ->
            try {
                EffectData(
                    name = row[nameIdx],
                    value = row[valueIdx].toDouble(),
                    baseCost = row[costIdx].toDouble(),
                    baseMagnitude = row[magnitudeIdx].toDouble(),
                    baseDuration = row[durationIdx].toDouble()
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun escapeHtml(text: String): String {
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;")
    }
}
