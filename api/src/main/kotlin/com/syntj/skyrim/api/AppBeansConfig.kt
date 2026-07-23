package com.syntj.skyrim.api

import com.syntj.skyrim.SkyrimPotionFinder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/** Wires up core-module classes as singleton Spring beans. */
@Configuration(proxyBeanMethods = false)
class AppBeansConfig {

    @Bean
    fun skyrimPotionFinder(): SkyrimPotionFinder = SkyrimPotionFinder()
}
