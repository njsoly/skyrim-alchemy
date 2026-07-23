package com.syntj.skyrim

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/** Which [IngredientSource] strategy the app should use, chosen via [AppConfig]. */
enum class IngredientSourceType {
    ENUM,
    JSON,
}

data class IngredientConfig(
    val source: IngredientSourceType = IngredientSourceType.JSON
)

data class AppConfig(
    val ingredient: IngredientConfig = IngredientConfig()
)

/** Loads [AppConfig] from an `application.yml` classpath resource, falling back to defaults. */
object AppConfigLoader {
    const val DEFAULT_RESOURCE = "application.yml"

    private val logger: Logger = LoggerFactory.getLogger(AppConfigLoader::class.java)

    private val objectMapper = ObjectMapper(YAMLFactory())
        .registerModule(kotlinModule())
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    fun load(resourcePath: String = DEFAULT_RESOURCE): AppConfig {
        val resourceStream = javaClass.classLoader.getResourceAsStream(resourcePath)
        if (resourceStream == null) {
            logger.warn("Could not find $resourcePath on the classpath; using default AppConfig.")
            return AppConfig()
        }

        return resourceStream.use { objectMapper.readValue(it, AppConfig::class.java) }
    }
}
