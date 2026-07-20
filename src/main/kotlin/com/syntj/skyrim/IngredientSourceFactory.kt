package com.syntj.skyrim

/** Chooses the [IngredientSource] strategy based on [AppConfig]. */
object IngredientSourceFactory {
    fun create(config: AppConfig = AppConfigLoader.load()): IngredientSource =
        when (config.ingredient.source) {
            IngredientSourceType.ENUM -> EnumIngredientSource()
            IngredientSourceType.JSON -> JsonIngredientSource()
        }
}
