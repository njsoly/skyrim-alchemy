package com.syntj.skyrim.domain

/**
 * Common shape shared by [Ingredient] (hardcoded enum) and [IngredientFromJson] (JSON-backed),
 * allowing the rest of the app to work with either source interchangeably.
 */
interface AlchemyIngredient {
    val displayName: String
    val weight: Double?
    val value: Double?
    val image: String?
    val effectNames: List<String>
}
