package com.syntj.skyrim

data class IngredientFromJson (
    val name: String,
    val effects: List<String>,
    val image: String,
    val value: Double,
    val weight: Double
)
