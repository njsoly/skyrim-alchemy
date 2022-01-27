package com.syntj.skyrim

open class SkyrimPotionRecipe_Enum(val ingredients: List<Ingredient>) {

    private val effects: List<Effect>

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
    }

    fun getPrice() : Double {
        return ingredients.sumOf { it.value ?: 0.0 }
    }

    fun getTotalWeight() : Double {
        return ingredients.sumOf { it.weight ?: 0.0 }
    }

    fun getEffects() : List<Effect> {
//        val allEffects = mutableListOf<Effect>()
//        ingredients.forEach { ingredient ->
//            ingredient.effects.forEach { effect ->
//                if (effect != null) {
//                    allEffects.add(effect)
//                }
//            }
//        }
//        val grouped: Map<Effect, List<Effect>> = allEffects.groupBy { it }
//                .filter { groupedEntry -> groupedEntry.value.size > 1 }
//        val effects = grouped.map { it.key }

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
