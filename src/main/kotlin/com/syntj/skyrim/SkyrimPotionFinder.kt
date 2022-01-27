package com.syntj.skyrim

import com.syntj.skyrim.SkyrimPotionFinder.Companion.logger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SkyrimPotionFinder (jsonPath: String = SkyrimAlchemyConstants.JSON_PATH) : SkyrimIngredientsAnalyzer(jsonPath) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SkyrimPotionFinder::class.java)

    }

    /**
     * Returns a list of all matches against any of this [ingredient][ingredientA]'s effects,
     * where the list contents are pairs, showing what ingredient matched, on what effect.
     *
     * So, if some ingredient matches this [ingredient][ingredientA] on more than one effect,
     * the second ingredient will appear in the list multiple times, one for each effect matched.
     */
    fun findMatchesFor_IngredientFromJson(ingredientA: IngredientFromJson) : List<Pair<IngredientFromJson, String>> {
        val matches = mutableListOf<Pair<IngredientFromJson, String>>()

        this.ingredients.minus(ingredientA).forEach { ingredientB ->
            ingredientB.effects.forEach { ingredientBEffect ->
                if (ingredientA.effects.contains(ingredientBEffect)) {
                    matches.add(Pair(ingredientB, ingredientBEffect))
                }
            }
        }

        return matches
    }


    fun bruteForceFindThreeIngredientFormulasWithMostEffects_json() : List<SkyrimPotionRecipe> {
        val recipeSets = mutableSetOf<Set<IngredientFromJson>>()

        ingredients.forEach { ingredientX ->
            ingredients.minus(ingredientX).forEach { ingredientY ->
                ingredients.minus(ingredientX).minus(ingredientY).forEach { ingredientZ ->
                    val recipe = SkyrimPotionRecipe(listOf(ingredientX, ingredientY, ingredientZ))
                    if (recipe.getEffects().size >= 3) {
                        recipeSets.add(recipe.ingredients.toSet())
                    }
                }
            }
        }

        val recipes = recipeSets.map { recipeSet -> SkyrimPotionRecipe(recipeSet.toList()) }

        return recipes.sortedByDescending {
            it.getEffects().size
        }
    }
    fun bruteForceFindThreeIngredientFormulasWithMostEffects_enum() : List<SkyrimPotionRecipe_Enum> {
        val recipeSets = mutableSetOf<Set<Ingredient>>()

        val allIngredients = Ingredient.values().toList()

        allIngredients.forEach { ingredientX ->
            allIngredients.minus(ingredientX).forEach { ingredientY ->
                allIngredients.minus(ingredientX).minus(ingredientY).forEach { ingredientZ ->
                    val recipe = SkyrimPotionRecipe_Enum(listOf(ingredientX, ingredientY, ingredientZ))
                    if (recipe.effects.size >= 3) {
                        recipeSets.add(recipe.ingredients.toSet())
                    }
                }
            }
        }

        val recipes = recipeSets.map { recipeSet -> SkyrimPotionRecipe_Enum(recipeSet.toList()) }

        return recipes.sortedByDescending {
            it.effects.size
        }
    }

    fun bruteForceFindTwoIngredientFormulasWithMostEffects_Json() : List<SkyrimPotionRecipe> {
        val recipeSets = mutableSetOf<Set<IngredientFromJson>>()

        ingredients.forEach { ingredientX ->
            ingredients.minus(ingredientX).forEach { ingredientY ->
                    val recipe = SkyrimPotionRecipe(listOf(ingredientX, ingredientY))
                    if (recipe.getEffects().size >= 2) {
                        recipeSets.add(recipe.ingredients.toSet())
                    }
            }
        }

        val recipes = recipeSets.map { recipeSet -> SkyrimPotionRecipe(recipeSet.toList()) }

        return recipes.sortedByDescending {
            it.getEffects().size
        }
    }


}

fun main() {
    val skyrimPotionFinder = SkyrimPotionFinder()

    val wheat = skyrimPotionFinder.ingredients.firstOrNull { it.name == "Wheat" }!!

    val wheatMatches: List<Pair<IngredientFromJson, String>> = skyrimPotionFinder.findMatchesFor_IngredientFromJson(wheat)
    logger.info("matches on ${wheat.name}: " +
        wheatMatches.map {
            "(${it.first.name}, ${it.second})"
        }.toString()
    )

    val restoreHealthRecipe = SkyrimPotionRecipe(
        listOf(
            skyrimPotionFinder.ingredientsByName["Wheat"]!!,
            skyrimPotionFinder.ingredientsByName["Blue Mountain Flower"]!!
        )
    )

    logger.info("Restore health recipe: \n${ restoreHealthRecipe.getStats() }")

    val twoIngredTimer = Timers.timerStart("twoIngredients")
    skyrimPotionFinder.bruteForceFindTwoIngredientFormulasWithMostEffects_Json()
    twoIngredTimer.stop()

    val threeIngredTimer_json = Timers.timerStart("threeIngredients_json")
    skyrimPotionFinder.bruteForceFindThreeIngredientFormulasWithMostEffects_json()
    threeIngredTimer_json.stop()

    val threeIngredTimer_enum = Timers.timerStart("threeIngredients_enum")
    val recipes3_enum = skyrimPotionFinder.bruteForceFindThreeIngredientFormulasWithMostEffects_enum()
    threeIngredTimer_enum.stop()

    val sb = StringBuffer("top effective potions for three ingredients: \n")
    recipes3_enum.slice(0..50).forEach{
        sb.append(it.getStats())
        sb.append("\n")
    }
    logger.info(sb.toString())

    logger.info("it took ${twoIngredTimer.elapsed()} us to brute force two ingredients.")
    logger.info("it took ${threeIngredTimer_json.elapsed()} us to brute force three ingredients (via json).")
    logger.info("it took ${threeIngredTimer_enum.elapsed()} us to brute force three ingredients (via enum).")
}
