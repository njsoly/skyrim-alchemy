package com.syntj.skyrim.api

import com.syntj.skyrim.SkyrimPotionFinder
import com.syntj.skyrim.api.dto.RecipeDto
import com.syntj.skyrim.api.dto.toDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/potions")
class PotionController(private val potionFinder: SkyrimPotionFinder) {

    @GetMapping("/top-two")
    fun topTwoIngredientRecipes(@RequestParam(defaultValue = "20") limit: Int): List<RecipeDto> =
        potionFinder.bruteForceFindTwoIngredientFormulasWithMostEffects()
            .take(limit)
            .map { it.toDto() }

    @GetMapping("/top-three")
    fun topThreeIngredientRecipes(@RequestParam(defaultValue = "20") limit: Int): List<RecipeDto> =
        potionFinder.bruteForceFindThreeIngredientFormulasWithMostEffects()
            .take(limit)
            .map { it.toDto() }
}
