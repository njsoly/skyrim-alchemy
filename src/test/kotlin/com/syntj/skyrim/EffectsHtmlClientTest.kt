package com.syntj.skyrim

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

/**
 * Unit test that exercises [EffectsHtmlClient.parseEffects] against the
 * local copies of `effects.html` and its referenced JS bundles in
 * `src/main/resources/powtions`, so it runs without any network access.
 */
class EffectsHtmlClientTest {

    private fun loadLocalEffectsDocument() =
        Jsoup.parse(File(effectsHtmlFixture().toURI()), "UTF-8")

    private fun effectsHtmlFixture() =
        javaClass.getResource("/powtions/effects.html")
            ?: error("Could not find test fixture /powtions/effects.html on classpath")

    @Test
    fun `parseEffects extracts every effect from the local fixture`() {
        val effects = EffectsHtmlClient().parseEffects(loadLocalEffectsDocument())

        assertEquals(60, effects.size)
        assertEquals(60, effects.map { it.name }.toSet().size)
    }

    @Test
    fun `parseEffects correctly maps each field`() {
        val effects = EffectsHtmlClient().parseEffects(loadLocalEffectsDocument())

        val cureDisease = effects.single { it.name == "Cure Disease" }
        assertEquals(21.0, cureDisease.value, 1e-9)
        assertEquals(0.5, cureDisease.baseCost, 1e-9)
        assertEquals(5.0, cureDisease.baseMagnitude, 1e-9)
        assertEquals(0.0, cureDisease.baseDuration, 1e-9)

        val weaknessToShock = effects.single { it.name == "Weakness to Shock" }
        assertEquals(56.0, weaknessToShock.value, 1e-9)
        assertEquals(0.7, weaknessToShock.baseCost, 1e-9)
        assertEquals(3.0, weaknessToShock.baseMagnitude, 1e-9)
        assertEquals(30.0, weaknessToShock.baseDuration, 1e-9)
    }
}
