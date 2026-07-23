package com.syntj.skyrim

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EffectsHtmlClient(private val effectsUrl: String = EFFECTS_URL) {

    companion object {
        const val EFFECTS_URL = "https://www.powtions.com/effects"
        val logger: Logger = LoggerFactory.getLogger(EffectsHtmlClient::class.java)
    }

    fun fetchEffectsDocument(): Document {
        logger.info("Fetching effects page from $effectsUrl")
        return Jsoup.connect(effectsUrl).get()
    }

    /**
     * Fetches the effects page and returns the first table found on it as a
     * list of rows, where each row is a list of the trimmed text content of
     * its cells (both header `<th>` and data `<td>` cells).
     */
    fun fetchEffectsTableRows(): List<List<String>> {
        val document = fetchEffectsDocument()
        val table = document.selectFirst("table")
            ?: throw IllegalStateException("No table found on page at $effectsUrl")

        return table.select("tr").map { row: Element ->
            row.select("th, td").map { cell -> cell.text().trim() }
        }
    }
}
