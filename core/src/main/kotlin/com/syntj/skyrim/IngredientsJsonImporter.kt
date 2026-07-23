package com.syntj.skyrim

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.syntj.skyrim.domain.IngredientFromJson
import com.syntj.skyrim.domain.IngredientList
import com.syntj.skyrim.domain.SkyrimAlchemyConstants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class IngredientsJsonImporter {

    companion object {
        val objectMapper = ObjectMapper().registerModule(kotlinModule())
        val logger : Logger = LoggerFactory.getLogger(IngredientsJsonImporter::class.java)
    }

    fun readIngredientsJson(path: String = SkyrimAlchemyConstants.JSON_PATH): List<IngredientFromJson> {
        logger.info("Reading ingredients from $path")

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
