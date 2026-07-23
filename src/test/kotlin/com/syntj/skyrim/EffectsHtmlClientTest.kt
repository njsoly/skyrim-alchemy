package com.syntj.skyrim

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EffectsHtmlClientTest {

    @Test
    fun `fetchEffects returns list of EffectData objects rendered from local effects html and js`() {
        val client = EffectsHtmlClient("src/main/resources/powtions/effects.html")
        val effects = client.fetchEffects()

        assertNotNull(effects)
        assertEquals(60, effects.size)

        val cureDisease = effects.find { it.name == "Cure Disease" }
        assertNotNull(cureDisease)
        assertEquals(21.0, cureDisease!!.value, 0.001)
        assertEquals(0.5, cureDisease.baseCost, 0.001)
        assertEquals(5.0, cureDisease.baseMagnitude, 0.001)
        assertEquals(0.0, cureDuration(cureDisease), 0.001)
    }

    private fun cureDuration(cureDisease: EffectData): Double = cureDisease.baseDuration

    @Test
    fun `fetchEffectsTableRows renders table and returns rows from document`() {
        val client = EffectsHtmlClient("src/main/resources/powtions/effects.html")
        val rows = client.fetchEffectsTableRows()

        assertNotNull(rows)
        assertTrue("Table should have at least header + data rows", rows.size > 1)

        val header = rows.first()
        assertEquals("Name", header[0])
        assertEquals("Value", header[1])
        assertEquals("Base cost", header[2])
        assertEquals("Base magnitude", header[3])
        assertEquals("Base duration", header[4])

        val firstDataRow = rows[1]
        assertEquals("Cure Disease", firstDataRow[0])
    }

    @Test
    fun `EffectsService getEffects returns List of EffectData using EffectsHtmlClient`() {
        val client = EffectsHtmlClient("src/main/resources/powtions/effects.html")
        val service = EffectsService(client)
        val effects = service.getEffects()

        assertEquals(60, effects.size)
        val cureDisease = effects.first { it.name == "Cure Disease" }
        assertEquals("Cure Disease", cureDisease.name)
        assertEquals(21.0, cureDisease.value, 0.001)
        assertEquals(0.5, cureDisease.baseCost, 0.001)
        assertEquals(5.0, cureDisease.baseMagnitude, 0.001)
        assertEquals(0.0, cureDisease.baseDuration, 0.001)
    }
}
