package com.syntj.skyrim

import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class EffectData(
    val name: String,
    val value: Double,
    val baseCost: Double,
    val baseMagnitude: Double,
    val baseDuration: Double,
)

class EffectsService(private val effectsHtmlClient: EffectsHtmlClient = EffectsHtmlClient()) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(EffectsService::class.java)
    }

    fun getEffects(): List<EffectData> {
        return effectsHtmlClient.fetchEffectsTableRows()
    }

    private fun parseEffects(rows: List<List<String>>): List<EffectData> {
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
            toEffectData(row, nameIdx, valueIdx, costIdx, magnitudeIdx, durationIdx)
        }
    }

    private fun toEffectData(
        row: List<String>,
        nameIdx: Int,
        valueIdx: Int,
        costIdx: Int,
        magnitudeIdx: Int,
        durationIdx: Int,
    ): EffectData? {
        val maxIdx = listOf(nameIdx, valueIdx, costIdx, magnitudeIdx, durationIdx).maxOrNull()!!
        if (row.size <= maxIdx) {
            logger.warn("Skipping malformed effect row: $row")
            return null
        }

        return try {
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
