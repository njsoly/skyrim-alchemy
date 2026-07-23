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
        return effectsHtmlClient.fetchEffects()
    }
}
