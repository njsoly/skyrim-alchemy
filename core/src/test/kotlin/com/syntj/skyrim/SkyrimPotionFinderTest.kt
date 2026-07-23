package com.syntj.skyrim

import org.junit.Assert.assertEquals
import org.junit.Test

class SkyrimPotionFinderTest {

    @Test
    fun test() {
        val x = SkyrimPotionFinder()
        print(x.twoIngredCount)
        assertEquals(0, x.twoIngredCount)
    }
}
