package com.syntj.skyrim

fun main() {
    println("hi there")

    val ingredients = IngredientsJsonImporter().readIngredientsJson()

    val mostExpensive = ingredients.maxByOrNull{ it.value }
    val heaviest = ingredients.maxByOrNull { it.weight }
    val zeroWeight = ingredients.filter { ingredient -> ingredient.weight == 0.0 }
    val zeroValue = ingredients.filter { ingredient -> ingredient.value == 0.0 }
    println("ingredients has ${ingredients.size} in it.")
    println("most expensive is ${mostExpensive?.name}, at ${mostExpensive?.value}.")
    println("heaviest is ${heaviest?.name}, at ${heaviest!!.weight}.")
}