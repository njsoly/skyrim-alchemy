package com.syntj.skyrim

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URI
import java.util.regex.Pattern
import kotlin.text.Charsets

class EffectsHtmlClient(private val effectsUrl: String = EFFECTS_URL) {

    companion object {
        const val EFFECTS_URL = "https://www.powtions.com/effects"
        val logger: Logger = LoggerFactory.getLogger(EffectsHtmlClient::class.java)
    }

    fun fetchEffectsDocument(): Document {
        logger.info("Fetching effects page from $effectsUrl")
        if (effectsUrl.startsWith("http://") || effectsUrl.startsWith("https://")) {
            return Jsoup.connect(effectsUrl).get()
        }
        val uriPath = if (effectsUrl.startsWith("file:")) URI(effectsUrl).path else effectsUrl
        val file = File(uriPath)
        if (file.exists()) {
            return Jsoup.parse(file, "UTF-8", file.toURI().toString())
        }
        val resPath = if (effectsUrl.startsWith("/")) effectsUrl else "/$effectsUrl"
        val stream = this::class.java.getResourceAsStream(resPath)
            ?: throw IllegalArgumentException("Could not find resource or file: $effectsUrl")
        return Jsoup.parse(stream, "UTF-8", effectsUrl)
    }

    /**
     * Fetches the effects page and returns the first table found on it as a
     * list of rows, where each row is a list of the trimmed text content of
     * its cells (both header `<th>` and data `<td>` cells).
     */
    fun fetchEffectsTableRows(): List<EffectData> = fetchEffects()

    fun getEffects(): List<EffectData> = fetchEffects()

    fun fetchEffects(): List<EffectData> {
        val document = fetchEffectsDocument()
        val table = document.selectFirst("table")
        if (table != null) {
            val rows = table.select("tr").map { row: Element ->
                row.select("th, td").map { cell -> cell.text().trim() }
            }
            return parseTableRows(rows)
        }
        val scriptSrcs = document.select("script[src]").map { it.attr("src") }
        for (src in scriptSrcs) {
            val content = loadScriptContent(src)
            if (!content.isNullOrBlank()) {
                val effects = extractEffectsFromJs(content)
                if (effects.isNotEmpty()) {
                    return effects
                }
            }
        }
        return emptyList()
    }

    private fun parseTableRows(rows: List<List<String>>): List<EffectData> {
        if (rows.isEmpty()) {
            logger.warn("Effects table has no rows.")
            return emptyList()
        }

        val header = rows.first()
        val nameIdx = header.indexOfFirst { it.equals("Name", ignoreCase = true) }
        val valueIdx = header.indexOfFirst { it.equals("Value", ignoreCase = true) }
        val costIdx = header.indexOfFirst { it.equals("Base cost", ignoreCase = true) }
        val magnitudeIdx = header.indexOfFirst { it.equals("Base magnitude", ignoreCase = true) }
        val durationIdx = header.indexOfFirst { it.equals("Base duration", ignoreCase = true) }

        if (listOf(nameIdx, valueIdx, costIdx, magnitudeIdx, durationIdx).any { it < 0 }) {
            logger.warn("Effects table header did not contain the expected columns: $header")
            return emptyList()
        }

        return rows.drop(1).mapNotNull { row ->
            val maxIdx = listOf(nameIdx, valueIdx, costIdx, magnitudeIdx, durationIdx).maxOrNull()!!
            if (row.size <= maxIdx) {
                logger.warn("Skipping malformed effect row: $row")
                null
            } else {
                try {
                    EffectData(
                        name = row[nameIdx],
                        value = row[valueIdx].toDouble(),
                        baseCost = row[costIdx].toDouble(),
                        baseMagnitude = row[magnitudeIdx].toDouble(),
                        baseDuration = row[durationIdx].toDouble(),
                    )
                } catch (e: NumberFormatException) {
                    logger.warn("Skipping effect row with unparseable numbers: $row", e)
                    null
                }
            }
        }
    }

    private fun loadScriptContent(src: String): String? {
        if (effectsUrl.startsWith("http://") || effectsUrl.startsWith("https://")) {
            return try {
                val url = URI(effectsUrl).resolve(src).toString()
                Jsoup.connect(url).ignoreContentType(true).maxBodySize(0).execute().body()
            } catch (e: Exception) {
                logger.warn("Failed to fetch remote script $src", e)
                null
            }
        }
        val fileName = File(src).name
        if (!effectsUrl.startsWith("http://") && !effectsUrl.startsWith("https://")) {
            val basePath = if (effectsUrl.startsWith("file:")) URI(effectsUrl).path else effectsUrl
            val parentDir = File(basePath).parentFile
            if (parentDir != null) {
                val candidate = File(parentDir, fileName)
                if (candidate.exists()) {
                    return candidate.readText(Charsets.UTF_8)
                }
            }
        }
        val directFile = File(src)
        if (directFile.exists()) {
            return directFile.readText(Charsets.UTF_8)
        }
        val fallbackFile = File("src/main/resources/powtions/$fileName")
        if (fallbackFile.exists()) {
            return fallbackFile.readText(Charsets.UTF_8)
        }
        val resStream = this::class.java.getResourceAsStream("/powtions/$fileName")
            ?: this::class.java.getResourceAsStream(if (src.startsWith("/")) src else "/$src")
        if (resStream != null) {
            return resStream.bufferedReader(Charsets.UTF_8).readText()
        }
        return null
    }

    private fun extractEffectsFromJs(content: String): List<EffectData> {
        val effects = mutableListOf<EffectData>()
        val blockPattern = Pattern.compile("""\{\s*name:\s*"[^"]+"[^}]*?baseCost:\s*[0-9.]+[^\}]*?\}""")
        val matcher = blockPattern.matcher(content)
        while (matcher.find()) {
            val block = matcher.group()
            val name = extractGroup(block, """name:\s*"([^"]+)"""")
            val valStr = extractGroup(block, """(?:valueAt100Skill|value):\s*([0-9.]+)""")
            val costStr = extractGroup(block, """(?:baseCost|cost):\s*([0-9.]+)""")
            val magStr = extractGroup(block, """(?:baseMagnitude|magnitude):\s*([0-9.]+)""")
            val durStr = extractGroup(block, """(?:baseDuration|duration):\s*([0-9.]+)""")
            if (name != null && valStr != null && costStr != null && magStr != null && durStr != null) {
                try {
                    effects.add(
                        EffectData(
                            name = name,
                            value = parseDoubleSafe(valStr),
                            baseCost = parseDoubleSafe(costStr),
                            baseMagnitude = parseDoubleSafe(magStr),
                            baseDuration = parseDoubleSafe(durStr)
                        )
                    )
                } catch (e: NumberFormatException) {
                    logger.warn("Skipping effect block with unparseable numbers: $block", e)
                }
            }
        }
        return effects
    }

    private fun extractGroup(text: String, regex: String): String? {
        val matcher = Pattern.compile(regex).matcher(text)
        return if (matcher.find()) matcher.group(1) else null
    }

    private fun parseDoubleSafe(str: String): Double {
        val trimmed = str.trim()
        val normalized = if (trimmed.startsWith(".")) "0$trimmed" else trimmed
        return normalized.toDouble()
    }
}
