package com.syntj.skyrim

class SkyrimIngredientsAnalyzer {

//    val ingredientList: IngredientList

    fun run() {
        println("hi there, welcome to ")

        val ingredients = IngredientsJsonImporter().readIngredientsJson()

        val mostExpensive = ingredients.maxByOrNull{ it.value }!!
        val heaviest = ingredients.maxByOrNull { it.weight }!!
        val zeroWeight = ingredients.filter { ingredient -> ingredient.weight == 0.0 }
        val zeroValue = ingredients.filter { ingredient -> ingredient.value == 0.0 }
        println("ingredients has ${ingredients.size} in it.")
        println("most expensive is ${mostExpensive?.name}, at ${mostExpensive.value}.")
        println("heaviest is ${heaviest?.name}, at ${heaviest.weight}.")

        if (zeroWeight.isNotEmpty()) {
            println(
                "${zeroWeight.size} ingredients have no weight: \n" +
                        zeroWeight.map { it.name }.joinToString(separator = ",\n")
            )
        } else {
            println(
                "${zeroWeight.size} ingredients have no weight.")

        }
    }
}

fun main() {
   SkyrimIngredientsAnalyzer().run()
}