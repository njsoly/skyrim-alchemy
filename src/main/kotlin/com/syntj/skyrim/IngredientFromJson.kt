package com.syntj.skyrim

data class IngredientFromJson (
    val name: String,
    val effects: List<String>,
    override val image: String,
    override val value: Double,
    override val weight: Double
) : AlchemyIngredient {
    override val displayName: String get() = name
    override val effectNames: List<String> get() = effects
}
