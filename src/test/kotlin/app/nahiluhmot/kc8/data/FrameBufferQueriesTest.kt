package app.nahiluhmot.kc8.data

import app.nahiluhmot.kc8.Constants
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalUnsignedTypes::class)
class FrameBufferQueriesTest {
    @Test
    fun testIsPixelOn() {
        val frameBuffer = ULongArray(Constants.SCREEN_HEIGHT)

        frameBuffer[1] = 4uL
        frameBuffer[2] = 1uL

        for (x in 0 until Constants.SCREEN_WIDTH) {
            for (y in 0 until Constants.SCREEN_HEIGHT) {
                val isOn = FrameBufferQueries.isPixelOn(frameBuffer, x, y)

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
        for (x in 0 until Constants.SCREEN_WIDTH) {
            for (y in 0 until Constants.SCREEN_HEIGHT) {
                assertTrue(
                    FrameBufferQueries.areCoordinatesValid(x, y),
                    "Expected ($x, $y) to be valid coordinates"
                )
            }
        }

        for (x in listOf(-2, -1, Constants.SCREEN_WIDTH, Constants.SCREEN_WIDTH + 1)) {
            for (y in 0 until Constants.SCREEN_HEIGHT) {
                assertFalse(
                    FrameBufferQueries.areCoordinatesValid(x, y),
                    "Expected ($x, $y) to not be valid coordinates"
                )
            }
        }

        for (x in 0 until Constants.SCREEN_WIDTH) {
            for (y in listOf(-2, -1, Constants.SCREEN_HEIGHT, Constants.SCREEN_HEIGHT + 1)) {
                assertFalse(
                    FrameBufferQueries.areCoordinatesValid(x, y),
                    "Expected ($x, $y) to not be valid coordinates"
                )
            }
        }
    }
}