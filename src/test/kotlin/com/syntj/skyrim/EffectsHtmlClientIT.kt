package com.syntj.skyrim

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Integration test that hits the real powtions.com effects page over the
 * network. Run via `mvn verify` (or `mvn failsafe:integration-test`), since
 * this is excluded from the regular unit test (`mvn test`) run.
 *
 * NOTE: As of writing, https://www.powtions.com/effects is a client-rendered
 * SPA, so the raw HTML fetched by Jsoup does not contain a `<table>` with
 * effect data. This test only asserts the page is reachable until
 * EffectsHtmlClient is updated to pull data from the site's underlying API
 * (or a JS-capable fetcher is used).
 */
class EffectsHtmlClientIT {

    companion object {

    val LINES = (
            "Cure Disease\t21\t0.5\t5\t0\n" +
            "Cure Poison\t3\t0.2\t2\t0\n" +
            "Damage Health\t3\t3\t2\t1\n" +
            "Damage Magicka\t52\t2.2\t3\t0\n" +
            "Damage Magicka Regen\t265\t0.5\t100\t5\n" +
            "Damage Stamina\t43\t1.8\t3\t0\n" +
            "Damage Stamina Regen\t159\t0.3\t100\t5\n" +
            "Fear\t120\t5\t1\t30\n" +
            "Fortify Alteration\t47\t0.2\t4\t60\n" +
            "Fortify Barter\t48\t2\t1\t30\n" +
            "Fortify Block\t118\t0.5\t4\t60\n" +
            "Fortify Carry Weight\t208\t0.15\t4\t300\n" +
            "Fortify Conjuration\t75\t0.25\t5\t60\n" +
            "Fortify Destruction\t151\t0.5\t5\t60\n" +
            "Fortify Enchanting\t14\t0.6\t1\t30\n" +
            "Fortify Health\t82\t0.35\t4\t60\n" +
            "Fortify Heavy Armor\t55\t0.5\t2\t60\n" +
            "Fortify Illusion\t94\t0.4\t4\t60\n" +
            "Fortify Light Armor\t55\t0.5\t2\t60\n" +
            "Fortify Lockpicking\t25\t0.5\t2\t30\n" +
            "Fortify Magicka\t71\t0.3\t4\t60\n" +
            "Fortify Marksman\t118\t0.5\t4\t60\n" +
            "Fortify One-handed\t118\t0.5\t4\t60\n" +
            "Fortify Persuasion\t1\t0.5\t1\t30\n" +
            "Fortify Pickpocket\t118\t0.5\t4\t60\n" +
            "Fortify Restoration\t118\t0.5\t4\t60\n" +
            "Fortify Smithing\t82\t0.75\t4\t30\n" +
            "Fortify Sneak\t118\t0.5\t4\t60\n" +
            "Fortify Stamina\t71\t0.3\t4\t60\n" +
            "Fortify Two-handed\t118\t0.5\t4\t60\n" +
            "Frenzy\t107\t15\t1\t10\n" +
            "Invisibility\t261\t100\t0\t4\n" +
            "Light\t25\t1\t1\t1\n" +
            "Lingering Damage Health\t86\t12\t1\t10\n" +
            "Lingering Damage Magicka\t71\t10\t1\t10\n" +
            "Lingering Damage Stamina\t12\t1.8\t1\t10\n" +
            "Night Eye\t38\t1\t1\t1\n" +
            "Paralysis\t285\t500\t0\t1\n" +
            "Ravage Health\t6\t0.4\t2\t10\n" +
            "Ravage Magicka\t15\t1\t2\t10\n" +
            "Ravage Stamina\t24\t1.6\t2\t10\n" +
            "Regenerate Health\t177\t0.1\t5\t300\n" +
            "Regenerate Magicka\t177\t0.1\t5\t300\n" +
            "Regenerate Stamina\t177\t0.1\t5\t300\n" +
            "Resist Fire\t86\t0.5\t3\t60\n" +
            "Resist Frost\t86\t0.5\t3\t60\n" +
            "Resist Magic\t51\t1\t1\t60\n" +
            "Resist Poison\t118\t0.5\t4\t60\n" +
            "Resist Shock\t86\t0.5\t3\t60\n" +
            "Restore Health\t21\t0.5\t5\t0\n" +
            "Restore Magicka\t25\t0.6\t5\t0\n" +
            "Restore Stamina\t25\t0.6\t5\t0\n" +
            "Slow\t247\t1\t50\t5\n" +
            "Spell Absorption\t380\t1\t1\t1\n" +
            "Waterbreathing\t100\t30\t0\t5\n" +
            "Weakness to Fire\t48\t0.6\t3\t30\n" +
            "Weakness to Frost\t40\t0.5\t3\t30\n" +
            "Weakness to Magic\t51\t1\t2\t30\n" +
            "Weakness to Poison\t51\t1\t2\t30\n" +
            "Weakness to Shock\t56\t0.7\t3\t30"
        ).split('\n')

        val DEFAULT_RESOURCE_PATH = "powtions/effects.html"

        val EFFECTS = LINES.map { it.split("\t") }

        val EXPECTED_EFFECT_DATA = EFFECTS.map { fields ->
            EffectData(
                name = fields[0],
                value = fields[1].toDouble(),
                baseCost = fields[2].toDouble(),
                baseMagnitude = fields[3].toDouble(),
                baseDuration = fields[4].toDouble(),
            )
        }
    }

    @Test
    fun `fetchEffectsDocument successfully reaches the effects page`() {
        val client = EffectsHtmlClient()

        val document = client.fetchEffectsDocument()

        assertEquals(200, document.connection().response().statusCode())
        assertTrue("Expected a non-empty HTML document", document.html().isNotBlank())
    }

    @Test
    fun `EffectsService getEffects reaches the live powtions effects page and returns all known effects`() {
        val effectsService = EffectsService()

        val effects = effectsService.getEffects()
        println("effects size: ${effects.size}")

        assertTrue(EXPECTED_EFFECT_DATA.size >= effects.size)
        assertTrue(effects.size >= 55)
        effects.forEach{ effect ->
            assertTrue(EXPECTED_EFFECT_DATA.contains(effect))
        }
    }

    @Test
    fun `fetchEffects renders the local powtions effects page (including its JS) into EffectData objects`() {
        val client = localEffectsHtmlClient()
        val effectsService = EffectsService(client)

        val effects = effectsService.getEffects()

        assertTrue(EXPECTED_EFFECT_DATA.size >= effects.size)
        assertTrue(effects.size >= 55)
        effects.forEach{ effect ->
            assertTrue(EXPECTED_EFFECT_DATA.contains(effect))
        }
    }

    @Test
    fun `fetchEffects parses known effect Cure Disease with correct stats from the rendered table`() {
        val client = localEffectsHtmlClient()
        val effectsService = EffectsService(client)

        val effects = effectsService.getEffects()

        val cureDisease = effects.firstOrNull { it.name == "Cure Disease" }
            ?: throw AssertionError("Expected an EffectData named 'Cure Disease' in the rendered table")

        assertEquals(21.0, cureDisease.value, 0.0001)
        assertEquals(0.5, cureDisease.baseCost, 0.0001)
        assertEquals(5.0, cureDisease.baseMagnitude, 0.0001)
        assertEquals(0.0, cureDisease.baseDuration, 0.0001)
    }


    /**
     * powtions.com/effects serves a client-rendered React app: the raw HTML
     * (mirrored locally at src/main/resources/powtions/effects.html) has no
     * `<table>` at all; the table is built by the two referenced JS chunk
     * files (also mirrored locally alongside effects.html). These tests
     * verify EffectsHtmlClient renders that local page (JS included) and
     * returns the table contents as `EffectData` objects, rather than just
     * scraping a `<table>` that doesn't exist in the raw HTML.
     */
    private fun localEffectsHtmlClient(): EffectsHtmlClient {
        val resourceUrl = EffectsHtmlClient::class.java.classLoader.getResource(DEFAULT_RESOURCE_PATH)
            ?: throw AssertionError("Could not find test resource $DEFAULT_RESOURCE_PATH on the classpath")
        return EffectsHtmlClient(effectsUrl = resourceUrl.toString())
    }

}
