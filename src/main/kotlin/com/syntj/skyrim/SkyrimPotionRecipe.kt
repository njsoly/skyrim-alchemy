package com.syntj.skyrim

open class SkyrimPotionRecipe(val ingredients: List<AlchemyIngredient>) {

    fun getPrice() : Double {
        return ingredients.sumOf { it.value ?: 0.0 }
    }

    fun getTotalWeight() : Double {
        return ingredients.sumOf { it.weight ?: 0.0 }
    }

    fun getEffects() : List<String> {
        val allEffects = mutableListOf<String>()
        ingredients.forEach { ingredient ->
            ingredient.effectNames.forEach { effect ->
                allEffects.add(effect)
            }
        }
        val grouped = allEffects.groupBy { it }.filter{ groupedEntry -> groupedEntry.value.size > 1 }
        val effects = grouped.map { it.key }

        return effects
    }

    fun getStats() : String {
        val sb = StringBuffer()
        sb.append("Ingredients: ${ingredients.map { it.displayName }}\n")
        sb.append("Weight: ${getTotalWeight()}, Price: ${getPrice()}\n")
        sb.append("Effects: ${getEffects()}\n")

        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is SkyrimPotionRecipe) {
            false
        } else {
            this.ingredients.toSet() == other.ingredients.toSet()
        }
    }

    override fun hashCode(): Int {
        return ingredients.toSet().hashCode()
    }

}
