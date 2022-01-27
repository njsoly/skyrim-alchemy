package com.syntj.skyrim

open class SkyrimPotionRecipe_Enum(val ingredients: List<Ingredient>) {

    val effects: List<Effect>
    val price: Double
    val totalWeight: Double

    init {
        val allEffects = mutableListOf<Effect>()
        ingredients.forEach { ingredient ->
            ingredient.effects.forEach { effect ->
                if (effect != null) {
                    allEffects.add(effect)
                }
            }
        }
        val grouped: Map<Effect, List<Effect>> = allEffects.groupBy { it }
            .filter { groupedEntry -> groupedEntry.value.size > 1 }
        effects = grouped.map { it.key }

        price = ingredients.sumOf { it.value ?: 0.0 }
        totalWeight = ingredients.sumOf { it.weight ?: 0.0 }
    }

    fun getStats() : String {
        val sb = StringBuffer()
        sb.append("Ingredients: ${this.ingredients.map { it.name }}\n")
        sb.append("Weight: ${this.totalWeight}, Price: ${this.price}\n")
        sb.append("Effects: ${this.effects}\n")

        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is SkyrimPotionRecipe_Enum) {
            false
        } else {
            this.ingredients.toSet() == other.ingredients.toSet()
        }
    }

    override fun hashCode(): Int {
        var result = ingredients.hashCode()
        result = 31 * result + effects.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + totalWeight.hashCode()
        return result
    }

}
