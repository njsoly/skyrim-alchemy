package com.syntj.skyrim

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.syntj.skyrim.SkyrimAlchemyConstants.JSON_PATH
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class IngredientsJsonImporter {

    companion object {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        val logger : Logger = LoggerFactory.getLogger(IngredientsJsonImporter::class.java)
    }

    fun readIngredientsJson(path: String = JSON_PATH): List<Ingredient> {

        checkIfFileIsReadable(path)

        return objectMapper.readValue(File(path), IngredientList::class.java).ingredients
    }

    private fun checkIfFileIsReadable(path: String) {
        when {
            !Files.exists(Path.of(path)) -> {
                throw Exception("Path $path doesn't exist")
            }
            !Files.isRegularFile(Path.of(path)) -> {
                throw Exception("Path $path is not a regular file.")
            }
            !Files.isReadable(Path.of(path)) -> {
                throw Exception("Path $path is not readable.")
            }
        }

    }
}
