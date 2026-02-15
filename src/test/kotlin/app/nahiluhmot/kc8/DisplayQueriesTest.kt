package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import app.nahiluhmot.kc8.Constants.SCREEN_WIDTH
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalUnsignedTypes::class)
class DisplayQueriesTest {
    @Test
    fun testIsPixelOn() {
        val display = ULongArray(SCREEN_HEIGHT)

        display[1] = 4uL
        display[2] = 1uL

        for (x in 0 until SCREEN_WIDTH) {
            for (y in 0 until SCREEN_HEIGHT) {
                val isOn = DisplayQueries.isPixelOn(display, x, y)

                if (((x == 2) && (y == 1)) || ((x == 0) && (y == 2))) {
                    assertTrue(isOn, "Expected the pixel at ($x, $y) to be on")
                } else {
                    assertFalse(isOn, "Expected the pixel at ($x, $y) to be off")
                }
            }
        }
    }
}