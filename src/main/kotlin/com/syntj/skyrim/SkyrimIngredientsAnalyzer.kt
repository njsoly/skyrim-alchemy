package com.syntj.skyrim

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.lang.Exception

open class SkyrimIngredientsAnalyzer (
    private val ingredientSource: IngredientSource = IngredientSourceFactory.create()
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SkyrimIngredientsAnalyzer::class.java)
    }

    val ingredients: List<AlchemyIngredient>
    val ingredientsByName: Map<String, AlchemyIngredient>
    val allEffectNames: List<String>
    val ingredientsByEffect: Map<String, List<AlchemyIngredient>>
    val ingredientsByCategory: Map<String, List<AlchemyIngredient>>
    val effectsByCategory: Map<String, List<String>>


    init {
        try {
            ingredients = ingredientSource.loadIngredients()
        } catch (ioe: IOException) {
            logger.error("IO exception reading in ingredients: ${ioe.message}", ioe)
            throw ioe
        } catch(e: Exception) {
            logger.error("Problem loading ingredients: ${e.message}", e)
            throw e
        }

        allEffectNames = initializeEffectsList(ingredients)

        ingredientsByName = ingredients.associate { ingredient ->
            Pair(ingredient.displayName, ingredient)
        }

        ingredientsByEffect = allEffectNames.associate { effect ->
            Pair<String, List<AlchemyIngredient>>(
                effect,
                ingredients.filter { ingredient -> ingredient.effectNames.contains(effect) }
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

    private fun categorizeIngredients(ingredients: List<AlchemyIngredient>): Map<String, List<AlchemyIngredient>> {
        val categories = mutableMapOf<String, List<AlchemyIngredient>>()


        SkyrimAlchemyConstants.MISC_CATEGORIES.forEach{ miscCategory ->
            categories[miscCategory.key] = ingredients.filter { ingredient ->
                miscCategory.value.contains(ingredient.displayName)
            }
        }

        return categories
    }

    private fun initializeEffectsList(ingredientList: List<AlchemyIngredient>) : List<String> {
        val allEffects = mutableSetOf<String>()
        ingredientList.forEach { ingredient ->
            ingredient.effectNames.forEach { singleEffect ->
                allEffects.add(singleEffect)
            }
        }

        return allEffects.toList().sorted()
    }

    private fun printAnalysis(ingredients: List<AlchemyIngredient>) {
        logger.debug("ingredients has ${ingredients.size} in it.")

        val mostExpensive = ingredients.maxByOrNull{ it.value ?: 0.0 }!!
        val heaviest = ingredients.maxByOrNull { it.weight ?: 0.0 }!!
        val zeroWeight = ingredients.filter { ingredient -> ingredient.weight == 0.0 }
        val zeroValue = ingredients.filter { ingredient -> ingredient.value == 0.0 }

        logger.info("most expensive is ${mostExpensive.displayName}, at ${mostExpensive.value}.")
        logger.info("heaviest is ${heaviest.displayName}, at ${heaviest.weight}.")

        if (zeroWeight.isNotEmpty()) {
            logger.info(
                "${zeroWeight.size} ingredients have no weight: \n" +
                        zeroWeight.map { it.displayName }.joinToString(separator = ",\n")
            )
        } else {
            logger.info("${zeroWeight.size} ingredients have no weight.")
        }

        if (zeroValue.isNotEmpty()) {
            logger.info(
                "${zeroValue.size} ingredients have no value: \n" +
                        zeroValue.map { it.displayName }.joinToString(separator = ",\n")
            )
        } else {
            logger.info("${zeroValue.size} ingredients have no value.")
        }

        logger.info("There are ${allEffectNames.size} effects.")
    }

    fun run() {
        logger.info("hi there, welcome to ${this.javaClass.simpleName}")

        printAnalysis(ingredients)
        logger.info("ingredient 0: \n" + ingredients[0])

        printIngredientsInEnumForm()

        logger.info("goodbye from ${this.javaClass.simpleName}.")
    }

    private fun printIngredientsInEnumForm() {
        logger.info("all ingredients in Enum format: ")

        ingredients.forEach{ ingredient ->
            val sb = StringBuffer()
            sb.append("\t" + ingredient.displayName.replace(" ", "").replace("-", "").replace("'", ""))
            sb.append("(description = \"${ingredient.displayName}\", ")
            sb.append("weight = " + ingredient.weight + ", ")
            sb.append("value = " + ingredient.value + ", ")
            sb.append("image = \"" + ingredient.image + "\", ")
            sb.append("\n\t\teffects = listOf(")

//            sb.append(ingredient.effectNames.joinToString ( separator = ", " ))
            ingredient.effectNames.forEach { effect ->
                sb.append(Effect.values().first{ it.description == effect}.name + ", ")
            }
            sb.append(")\n),")


            println(sb.toString())
        }
    }
}

fun main() {
   SkyrimIngredientsAnalyzer().run()
}
