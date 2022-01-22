package com.syntj.skyrim

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.lang.Exception

class SkyrimIngredientsAnalyzer {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SkyrimIngredientsAnalyzer::class.java)
    }

    private val ingredients: List<Ingredient>
    private val ingredientsByName: Map<String, Ingredient>
    private val allEffectNames: List<String>
    private val ingredientsByEffect: Map<String, List<Ingredient>>
    private val ingredientsByCategory: Map<String, List<Ingredient>>
    private val effectsByCategory: Map<String, List<String>>


    init {
        try {
            ingredients = IngredientsJsonImporter().readIngredientsJson()
        } catch (ioe: IOException) {
            logger.error("IO exception reading in JSON: ${ioe.message}", ioe)
            throw ioe
        } catch(e: Exception) {
            logger.error("Problem reading ingredients JSON: ${e.message}", e)
            throw e
        }

        allEffectNames = initializeEffectsList(ingredients)

        ingredientsByName = ingredients.associate { ingredient ->
            Pair(ingredient.name, ingredient)
        }

        ingredientsByEffect = allEffectNames.associate { effect ->
            Pair<String, List<Ingredient>>(
                effect,
                ingredients.filter { ingredient -> ingredient.effects.contains(effect) }
            )
        }

        ingredientsByCategory = categorizeIngredients(ingredients)
        effectsByCategory = categorizeEffects(allEffectNames)
    }

    private fun categorizeEffects(allEffectNames: List<String>): Map<String, List<String>> {
        val categories = mutableMapOf<String, List<String>>()

        SkyrimAlchemyConstants.CATEGORY_KEYWORDS.forEach { categoryKeyword ->
            categories[categoryKeyword] = allEffectNames.filter { effectName ->
                effectName.contains(categoryKeyword)
            }
        }

        return categories
    }

    private fun categorizeIngredients(ingredients: List<Ingredient>): Map<String, List<Ingredient>> {
        val categories = mutableMapOf<String, List<Ingredient>>()


        SkyrimAlchemyConstants.MISC_CATEGORIES.forEach{ miscCategory ->
            categories[miscCategory.key] = ingredients.filter { ingredient ->
                miscCategory.value.contains(ingredient.name)
            }
        }

        return categories
    }

    private fun initializeEffectsList(ingredientList: List<Ingredient>) : List<String> {
        val allEffects = mutableSetOf<String>()
        ingredientList.forEach { ingredient ->
            ingredient.effects.forEach { singleEffect ->
                allEffects.add(singleEffect)
            }
        }

        return allEffects.toList().sorted()
    }

    private fun printAnalysis(ingredients: List<Ingredient>) {
        val mostExpensive = ingredients.maxByOrNull{ it.value }!!
        val heaviest = ingredients.maxByOrNull { it.weight }!!
        val zeroWeight = ingredients.filter { ingredient -> ingredient.weight == 0.0 }
        val zeroValue = ingredients.filter { ingredient -> ingredient.value == 0.0 }

        logger.info("most expensive is ${mostExpensive?.name}, at ${mostExpensive.value}.")
        logger.info("heaviest is ${heaviest?.name}, at ${heaviest.weight}.")

        if (zeroWeight.isNotEmpty()) {
            logger.info(
                "${zeroWeight.size} ingredients have no weight: \n" +
                        zeroWeight.map { it.name }.joinToString(separator = ",\n")
            )
        } else {
            logger.info("${zeroWeight.size} ingredients have no weight.")
        }

        if (zeroValue.isNotEmpty()) {
            logger.info(
                "${zeroValue.size} ingredients have no value: \n" +
                        zeroValue.map { it.name }.joinToString(separator = ",\n")
            )
        } else {
            logger.info("${zeroValue.size} ingredients have no value.")
        }

        logger.info("There are ${allEffectNames.size} effects.")
    }

    fun run() {
        logger.info("hi there, welcome to ${this.javaClass.simpleName}")

        printAnalysis(ingredients)

        logger.info("goodbye from ${this.javaClass.simpleName}.")
    }
}

fun main() {
   SkyrimIngredientsAnalyzer().run()
}