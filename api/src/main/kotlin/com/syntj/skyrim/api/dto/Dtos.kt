package com.syntj.skyrim.api.dto

import com.syntj.skyrim.domain.SkyrimPotionRecipe

data class IngredientMatchDto(
    val ingredientName: String,
    val effect: String,
)

data class RecipeDto(
    val ingredients: List<String>,
    val weight: Double,
    val price: Double,
    val effects: List<String>,
)

fun SkyrimPotionRecipe.toDto(): RecipeDto = RecipeDto(
    ingredients = ingredients.map { it.displayName },
    weight = getTotalWeight(),
    price = getPrice(),
    effects = getEffects(),
)
