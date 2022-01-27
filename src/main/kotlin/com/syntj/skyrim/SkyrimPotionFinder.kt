package com.syntj.skyrim

import com.syntj.skyrim.SkyrimPotionFinder.Companion.logger
import jdk.nashorn.internal.ir.FunctionCall
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class SkyrimPotionFinder (jsonPath: String = SkyrimAlchemyConstants.JSON_PATH) : SkyrimIngredientsAnalyzer(jsonPath) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SkyrimPotionFinder::class.java)

        val timerBegin: Long = LocalDateTime.now()
        fun timeStart(name: String) {

        }
    }

    /**
     * Returns a list of all matches against any of this [ingredient][ingredientA]'s effects,
     * where the list contents are pairs, showing what ingredient matched, on what effect.
     *
     * So, if some ingredient matches this [ingredient][ingredientA] on more than one effect,
     * the second ingredient will appear in the list multiple times, one for each effect matched.
     */
    fun findMatchesFor(ingredientA: IngredientFromJson) : List<Pair<IngredientFromJson, String>> {
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


    fun bruteForceFindThreeIngredientFormulasWithMostEffects() : List<SkyrimPotionRecipe> {
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
    fun bruteForceFindTwoIngredientFormulasWithMostEffects() : List<SkyrimPotionRecipe> {
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

    val wheatMatches: List<Pair<IngredientFromJson, String>> = skyrimPotionFinder.findMatchesFor(wheat)
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

//    val recipes = skyrimPotionFinder.bruteForceFindThreeIngredientFormulasWithMostEffects()
    val recipes = skyrimPotionFinder.bruteForceFindTwoIngredientFormulasWithMostEffects()
    logger.info("top effective potions: ")
    recipes.slice(0..50).forEach{
        logger.info(it.getStats())
    }
}
