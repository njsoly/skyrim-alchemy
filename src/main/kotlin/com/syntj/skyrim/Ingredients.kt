package com.syntj.skyrim

enum class Ingredients (
    val description: String,
    val weight: Double?,
    val value: Double?,
    val image: String?,
    val effects: List<String?>,
) {
    Wheat("Wheat", 0.1, 1.0, image = null, effects = listOf("Restore Health"))
}
