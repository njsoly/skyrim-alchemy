package com.syntj.skyrim

open class SkyrimPotionRecipe(val ingredients: List<Ingredient>) {

    fun getPrice() : Double {
        return ingredients.sumOf { it.value }
    }

    fun getTotalWeight() : Double {
        return ingredients.sumOf { it.weight }
    }

    fun getEffects() : List<String> {
        val allEffects = mutableListOf<String>()
        ingredients.forEach { ingredient ->
            ingredient.effects.forEach { effect ->
                allEffects.add(effect)
            }
        }
        val grouped = allEffects.groupBy { it }.filter{ groupedEntry -> groupedEntry.value.size > 1 }
        val effects = grouped.map { it.key }

        return effects
    }

    fun getStats() : String {
        val sb = StringBuffer()
        sb.append("Ingredients: ${ingredients.map { it.name }}\n")
        sb.append("Weight: ${getTotalWeight()}, Price: ${getPrice()}\n")
        sb.append("Effects: ${getEffects()}\n")

        return sb.toString()
    }

}
