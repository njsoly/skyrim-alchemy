package com.syntj.skyrim

/**
 * Debug utility: prints every ingredient's data in [com.syntj.skyrim.domain.Ingredient]
 * enum-literal form, useful when refreshing the hardcoded enum from the JSON source.
 *
 * Run separately from the main CLI, e.g.:
 * `java -cp cli-bootable.jar com.syntj.skyrim.PrintIngredientsAnalyzerCliKt`
 */
fun main() {
    SkyrimIngredientsAnalyzer().run()
}
