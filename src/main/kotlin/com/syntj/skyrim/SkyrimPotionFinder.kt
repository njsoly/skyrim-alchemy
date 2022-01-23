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
    fun findMatchesFor(ingredientA: Ingredient) : List<Pair<Ingredient, String>> {
        val matches = mutableListOf<Pair<Ingredient, String>>()

        this.ingredients.minus(ingredientA).forEach { ingredientB ->
            ingredientB.effects.forEach { ingredientBEffect ->
                if (ingredientA.effects.contains(ingredientBEffect)) {
                    matches.add(Pair(ingredientB, ingredientBEffect))
                }
            }
        }

        return matches
    }


}

fun main() {
    val skyrimPotionFinder = SkyrimPotionFinder()

    val wheat = skyrimPotionFinder.ingredients.firstOrNull { it.name == "Wheat" }!!

    val wheatMatches: List<Pair<Ingredient, String>> = skyrimPotionFinder.findMatchesFor(wheat)
    logger.info("matches on ${wheat.name}: " +
        wheatMatches.map {
            "(${it.first.name}, ${it.second})"
        }.toString()
    )
}
