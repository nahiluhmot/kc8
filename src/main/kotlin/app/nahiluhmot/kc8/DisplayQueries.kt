package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import app.nahiluhmot.kc8.Constants.SCREEN_WIDTH

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Queries made against a Display.
 */
object DisplayQueries {
    /**
     * Test whether a pixel is on.
     *
     * @param display the Display to test
     * @param x the column to test
     * @param y the row to test
     * @return true if the pixel is on, false otherwise
     */
    fun isPixelOn(display: Display, x: Int, y: Int): Boolean =
        areCoordinatesValid(x, y) &&
                (display[y] and (1uL shl x) != 0uL)

    /**
     * Test whether (x, y) coordinates are in bounds.
     *
     * @param x the x part of the coordinate
     * @param y the y part of the coordinate
     * @return true if they're in bounds, false otherwise
     */
    fun areCoordinatesValid(x: Int, y: Int): Boolean =
        (x in 0 until SCREEN_WIDTH) && (y in 0 until SCREEN_HEIGHT)
}