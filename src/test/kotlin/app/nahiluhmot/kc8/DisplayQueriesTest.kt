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

    @Test
    fun testAreCoordinatesValid() {
        for (x in 0 until SCREEN_WIDTH) {
            for (y in 0 until SCREEN_HEIGHT) {
                assertTrue(
                    DisplayQueries.areCoordinatesValid(x, y),
                    "Expected ($x, $y) to be valid coordinates"
                )
            }
        }

        for (x in listOf(-2, -1, SCREEN_WIDTH, SCREEN_WIDTH + 1)) {
            for (y in 0 until SCREEN_HEIGHT) {
                assertFalse(
                    DisplayQueries.areCoordinatesValid(x, y),
                    "Expected ($x, $y) to not be valid coordinates"
                )
            }
        }

        for (x in 0 until SCREEN_WIDTH) {
            for (y in listOf(-2, -1, SCREEN_HEIGHT, SCREEN_HEIGHT + 1)) {
                assertFalse(
                    DisplayQueries.areCoordinatesValid(x, y),
                    "Expected ($x, $y) to not be valid coordinates"
                )
            }
        }
    }
}