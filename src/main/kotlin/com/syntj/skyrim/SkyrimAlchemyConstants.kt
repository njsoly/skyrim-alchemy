@file:Suppress("unused")

package com.syntj.skyrim

object SkyrimAlchemyConstants {
    const val JSON_PATH = "data/ingredients.json"

    /** Shrooms. */
    val KNOWN_AS_MUSHROOMS: List<String> = listOf(
        "Bleeding Crown",
        "Blisterwort",
        "Imp Stool",
        "Namira's Rot",
        "Scaly Pholiota",
        "White Cap",
    )

    /** Stuff that can be planted in a purchased house's garden or greenhouse */
    val KNOWN_AS_PLANTABLE: List<String> = listOf(
        "Blisterwort",
        "Blue Mountain Flower",
        "Canis Root",
        "Imp Stool",
        "Red Mountain Flower",
        "Purple Mountain Flower",
        "Snowberries"
    )

    val MISC_CATEGORIES: Map<String, List<String>> = mapOf(
        Pair("Mushrooms", KNOWN_AS_MUSHROOMS),
        Pair("Plantable", KNOWN_AS_PLANTABLE),
    )

    val CATEGORY_KEYWORDS = listOf(
        "Damage",
        "Fortify",
        "Lingering",
        "Ravage",
        "Regenerate",
        "Resist",
        "Restore",
    )
}