package com.syntj.skyrim

import com.syntj.skyrim.domain.AlchemyIngredient
import com.syntj.skyrim.domain.Ingredient
import com.syntj.skyrim.domain.SkyrimAlchemyConstants

/** Strategy for producing the list of ingredients the app operates on.
 *
 * @see EnumIngredientSource
 * @see JsonIngredientSource
 */
interface IngredientSource {
    fun loadIngredients(): List<AlchemyIngredient>
}

/** Ingredient source backed by the hardcoded [com.syntj.skyrim.domain.Ingredient] enum. */
class EnumIngredientSource : IngredientSource {
    override fun loadIngredients(): List<AlchemyIngredient> = Ingredient.entries
}

/** Ingredient source backed by [IngredientsJsonImporter], reading from [jsonPath]. */
class JsonIngredientSource(
    private val jsonPath: String = SkyrimAlchemyConstants.JSON_PATH
) : IngredientSource {
    override fun loadIngredients(): List<AlchemyIngredient> =
        IngredientsJsonImporter().readIngredientsJson(jsonPath)
}
