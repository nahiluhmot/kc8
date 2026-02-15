package app.nahiluhmot.kc8

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
    fun isPixelOn(display: Display, x: Int, y: Int): Boolean {
        if ((x >= Constants.SCREEN_WIDTH) || (y >= Constants.SCREEN_HEIGHT)) {
            return false
        }

        val row = display[y]

        return ((row shr x) % 2u) == ZERO
    }
}