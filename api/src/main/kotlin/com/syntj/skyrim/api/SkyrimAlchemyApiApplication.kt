package com.syntj.skyrim.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SkyrimAlchemyApiApplication

fun main(args: Array<String>) {
    runApplication<SkyrimAlchemyApiApplication>(*args)
}
