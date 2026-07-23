package com.syntj.skyrim

import com.syntj.skyrim.domain.AlchemyIngredient
import com.syntj.skyrim.domain.SkyrimPotionRecipe
import com.syntj.skyrim.util.Timers
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val logger: Logger = LoggerFactory.getLogger("com.syntj.skyrim.Cli")

fun main() {
    val skyrimPotionFinder = SkyrimPotionFinder()

    val wheat = skyrimPotionFinder.ingredients.firstOrNull { it.displayName == "Wheat" }!!

    val wheatMatches: List<Pair<AlchemyIngredient, String>> = skyrimPotionFinder.findMatchesFor(wheat)
    logger.info("matches on ${wheat.displayName}: " +
        wheatMatches.map {
            "(${it.first.displayName}, ${it.second})"
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
    val recipes2 = skyrimPotionFinder.bruteForceFindTwoIngredientFormulasWithMostEffects()
    twoIngredTimer.stop()

    val threeIngredTimer = Timers.timerStart("threeIngredients")
    val recipes3 = skyrimPotionFinder.bruteForceFindThreeIngredientFormulasWithMostEffects()
    threeIngredTimer.stop()

    val sb = StringBuffer("top effective potions for three ingredients: \n")
    recipes3.slice(0..50).forEach{
        sb.append(it.getStats())
        sb.append("\n")
    }
    logger.info(sb.toString())

    logger.info("it took ${twoIngredTimer.elapsed()} us " +
            "to brute force 2 of ${skyrimPotionFinder.ingredients.size} ingredients " +
            "(${skyrimPotionFinder.twoIngredCount} mixes).")
    logger.info("it took ${threeIngredTimer.elapsed()} us " +
            "to brute force 3 of ${skyrimPotionFinder.ingredients.size} ingredients " +
            "(${skyrimPotionFinder.threeIngredCounter} mixes).")
}
