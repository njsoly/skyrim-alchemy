package com.syntj.skyrim

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SkyrimIngredientsAnalyzer {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SkyrimIngredientsAnalyzer::class.java)
    }

    fun run() {
        println("hi there, welcome to ${this.javaClass.simpleName}")

        val ingredients = IngredientsJsonImporter().readIngredientsJson()

        val mostExpensive = ingredients.maxByOrNull{ it.value }!!
        val heaviest = ingredients.maxByOrNull { it.weight }!!
        val zeroWeight = ingredients.filter { ingredient -> ingredient.weight == 0.0 }
        val zeroValue = ingredients.filter { ingredient -> ingredient.value == 0.0 }
        logger.info("ingredients has ${ingredients.size} in it.")
        logger.info("most expensive is ${mostExpensive?.name}, at ${mostExpensive.value}.")
        logger.info("heaviest is ${heaviest?.name}, at ${heaviest.weight}.")

        if (zeroWeight.isNotEmpty()) {
            logger.info(
                "${zeroWeight.size} ingredients have no weight: \n" +
                        zeroWeight.map { it.name }.joinToString(separator = ",\n")
            )
        } else {
            logger.info(
                "${zeroWeight.size} ingredients have no weight.")

        }
    }
}

fun main() {
   SkyrimIngredientsAnalyzer().run()
}