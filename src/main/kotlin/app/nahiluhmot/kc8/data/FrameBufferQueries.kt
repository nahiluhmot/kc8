package app.nahiluhmot.kc8.data

import app.nahiluhmot.kc8.Constants

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Queries made against a FrameBuffer.
 */
object FrameBufferQueries {
    /**
     * Test whether a pixel is on.
     *
     * @param frameBuffer the FrameBuffer to test
     * @param x the column to test
     * @param y the row to test
     * @return true if the pixel is on, false otherwise
     */
    fun isPixelOn(frameBuffer: FrameBuffer, x: Int, y: Int): Boolean =
        areCoordinatesValid(x, y) &&
                (frameBuffer[y] and (1uL shl x) != 0uL)

    /**
     * Test whether (x, y) coordinates are in bounds.
     *
     * @param x the x part of the coordinate
     * @param y the y part of the coordinate
     * @return true if they're in bounds, false otherwise
     */
    fun areCoordinatesValid(x: Int, y: Int): Boolean =
        (x in 0 until Constants.SCREEN_WIDTH) && (y in 0 until Constants.SCREEN_HEIGHT)
}