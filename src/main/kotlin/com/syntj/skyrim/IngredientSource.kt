package com.syntj.skyrim

/** Strategy for producing the list of ingredients the app operates on. */
interface IngredientSource {
    fun loadIngredients(): List<AlchemyIngredient>
}

/** Ingredient source backed by the hardcoded [Ingredient] enum. */
class EnumIngredientSource : IngredientSource {
    override fun loadIngredients(): List<AlchemyIngredient> = Ingredient.values().toList()
}

/** Ingredient source backed by [IngredientsJsonImporter], reading from [jsonPath]. */
class JsonIngredientSource(
    private val jsonPath: String = SkyrimAlchemyConstants.JSON_PATH
) : IngredientSource {
    override fun loadIngredients(): List<AlchemyIngredient> =
        IngredientsJsonImporter().readIngredientsJson(jsonPath)
}
