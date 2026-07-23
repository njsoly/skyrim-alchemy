package com.syntj.skyrim

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URI

class EffectsHtmlClient(private val effectsUrl: String = EFFECTS_URL) {

    companion object {
        const val EFFECTS_URL = "https://www.powtions.com/effects"
        val logger: Logger = LoggerFactory.getLogger(EffectsHtmlClient::class.java)

        /**
         * The effects page itself contains no `<table>`: it's a
         * client-rendered SPA whose `<script src>` bundles build the table
         * in the browser from an embedded JS array of effect objects, e.g.:
         *
         * `{ name: "Cure Disease", concoction: "Potion", description: "...",
         *    baseCost: .5, magnitude: 5, duration: 0, valueAt100Skill: 21, fixed: "duration" }`
         *
         * This matches each such object directly out of the bundle's JS
         * source, regardless of the surrounding minified variable names.
         */
        private val EFFECT_ENTRY_REGEX = Regex(
            """\{\s*name:\s*"([^"]+)".*?baseCost:\s*(-?[\d.]+).*?magnitude:\s*(-?[\d.]+).*?duration:\s*(-?[\d.]+).*?valueAt100Skill:\s*(-?[\d.]+).*?fixed:\s*"[^"]*"\s*\}""",
            RegexOption.DOT_MATCHES_ALL,
        )
    }

    fun fetchEffectsDocument(): Document {
        logger.info("Fetching effects page from $effectsUrl")
        return Jsoup.connect(effectsUrl).get()
    }

    /**
     * Fetches the effects page and extracts its effect data from the JS
     * bundles it references, since the page has no static `<table>` for
     * Jsoup to parse.
     */
    fun fetchEffects(): List<EffectData> {
        return parseEffects(fetchEffectsDocument())
    }

    /**
     * Parses the effect data referenced by an already-loaded effects
     * `Document` (either the real page or a local copy of `effects.html`)
     * by fetching each `<script src>` bundle it points to and extracting
     * the embedded effect entries from their JS source.
     */
    fun parseEffects(document: Document): List<EffectData> {
        val scriptSources = document.select("script[src]").map { it.attr("src") }

        val effects = scriptSources
            .mapNotNull { src ->
                runCatching { fetchScript(resolveScriptUrl(document, src)) }
                    .onFailure { logger.warn("Failed to fetch script $src", it) }
                    .getOrNull()
            }
            .flatMap { js -> EFFECT_ENTRY_REGEX.findAll(js).map(::toEffectData) }

        if (effects.isEmpty()) {
            throw IllegalStateException("No effect data found in scripts referenced by ${document.baseUri()}")
        }

        return effects
    }

    /**
     * Resolves a `<script src>` value against the document's base URI.
     * Local copies of the site's JS bundles are stored flat alongside
     * `effects.html`, rather than nested under `/static/js/` as they are on
     * the real site (and Jsoup sets the base URI of a document parsed from
     * a `File` to that file's plain path, not a `file:` URI), so any
     * non-`http(s)` base URI is resolved as a local file by name only.
     */
    private fun resolveScriptUrl(document: Document, src: String): String {
        if (src.startsWith("http://") || src.startsWith("https://")) {
            return src
        }

        val baseUri = document.baseUri()
        return if (baseUri.startsWith("http://") || baseUri.startsWith("https://")) {
            URI(baseUri).resolve(src).toString()
        } else {
            File(baseUri).toPath().resolveSibling(src.substringAfterLast('/')).toUri().toString()
        }
    }

    private fun fetchScript(scriptUrl: String): String {
        logger.info("Fetching script from $scriptUrl")
        return if (scriptUrl.startsWith("file:")) {
            File(URI(scriptUrl)).readText()
        } else {
            Jsoup.connect(scriptUrl).ignoreContentType(true).execute().body()
        }
    }

    private fun toEffectData(match: MatchResult): EffectData {
        val (name, baseCost, magnitude, duration, value) = match.destructured
        return EffectData(
            name = name,
            value = value.toDouble(),
            baseCost = baseCost.toDouble(),
            baseMagnitude = magnitude.toDouble(),
            baseDuration = duration.toDouble(),
        )
    }
}
