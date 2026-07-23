package com.syntj.skyrim.api

import com.syntj.skyrim.SkyrimPotionFinder
import com.syntj.skyrim.api.dto.IngredientMatchDto
import com.syntj.skyrim.domain.AlchemyIngredient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/ingredients")
class IngredientController(private val potionFinder: SkyrimPotionFinder) {

    @GetMapping
    fun getAllIngredients(): List<AlchemyIngredient> = potionFinder.ingredients

    @GetMapping("/{name}/matches")
    fun getMatchesFor(@PathVariable name: String): List<IngredientMatchDto> {
        val ingredient = potionFinder.ingredientsByName[name]
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown ingredient: $name")

        return potionFinder.findMatchesFor(ingredient).map { (matchedIngredient, effect) ->
            IngredientMatchDto(matchedIngredient.displayName, effect)
        }
    }
}
