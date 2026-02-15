package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import app.nahiluhmot.kc8.Constants.SCREEN_WIDTH

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Queries made against a Display.
 */
object DisplayQueries {
    private const val ZERO: ULong = 0u

    /**
     * Test whether a pixel is on.
     *
     * @param display the Display to test
     * @param x the column to test
     * @param y the row to test
     * @return true if the pixel is on, false otherwise
     */
    fun isPixelOn(display: Display, x: Int, y: Int): Boolean =
        (x in 0 until SCREEN_WIDTH) &&
                (y in 0 until SCREEN_HEIGHT) &&
                (((display[y] shr x) % 2u) > ZERO)
}