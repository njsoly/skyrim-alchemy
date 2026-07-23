package com.syntj.skyrim

import com.syntj.skyrim.domain.AlchemyIngredient
import com.syntj.skyrim.domain.SkyrimPotionRecipe
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SkyrimPotionFinder (ingredientSource: IngredientSource = IngredientSourceFactory.create()) : SkyrimIngredientsAnalyzer(ingredientSource) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SkyrimPotionFinder::class.java)
    }

    var twoIngredCount = 0
    var threeIngredCounter = 0

    /**
     * Returns a list of all matches against any of this [ingredient][ingredientA]'s effects,
     * where the list contents are pairs, showing what ingredient matched, on what effect.
     *
     * So, if some ingredient matches this [ingredient][ingredientA] on more than one effect,
     * the second ingredient will appear in the list multiple times, one for each effect matched.
     */
    fun findMatchesFor(ingredientA: AlchemyIngredient) : List<Pair<AlchemyIngredient, String>> {
        val matches = mutableListOf<Pair<AlchemyIngredient, String>>()

        this.ingredients.minus(ingredientA).forEach { ingredientB ->
            ingredientB.effectNames.forEach { ingredientBEffect ->
                if (ingredientA.effectNames.contains(ingredientBEffect)) {
                    matches.add(Pair(ingredientB, ingredientBEffect))
                }
            }
        }

        return matches
    }


    fun bruteForceFindThreeIngredientFormulasWithMostEffects() : List<SkyrimPotionRecipe> {
        threeIngredCounter = 0
        val recipeSets = mutableSetOf<Set<AlchemyIngredient>>()

        ingredients.forEach { ingredientX ->
            ingredients.minus(ingredientX).forEach { ingredientY ->
                ingredients.minus(ingredientX).minus(ingredientY).forEach { ingredientZ ->
                    val recipe = SkyrimPotionRecipe(listOf(ingredientX, ingredientY, ingredientZ))
                    if (recipe.getEffects().size >= 3) {
                        recipeSets.add(recipe.ingredients.toSet())
                    }

                    threeIngredCounter++
                }
            }
        }

        val recipes = recipeSets.map { recipeSet -> SkyrimPotionRecipe(recipeSet.toList()) }

        return recipes.sortedByDescending {
            it.getEffects().size
        }
    }

    fun bruteForceFindTwoIngredientFormulasWithMostEffects() : List<SkyrimPotionRecipe> {
        twoIngredCount = 0
        val recipeSets = mutableSetOf<Set<AlchemyIngredient>>()

        ingredients.forEach { ingredientX ->
            ingredients.minus(ingredientX).forEach { ingredientY ->
                twoIngredCount++
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
