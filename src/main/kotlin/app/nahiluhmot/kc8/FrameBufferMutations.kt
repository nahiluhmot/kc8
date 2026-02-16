package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import app.nahiluhmot.kc8.Constants.SCREEN_WIDTH

/**
 * Updates the FrameBuffer.
 */
@OptIn(ExperimentalUnsignedTypes::class)
object FrameBufferMutations {
    /**
     * Draws a Byte onto the FrameBuffer. Bytes are XORed onto the existing FrameBuffer.
     * We also have to reverse the sprites.
     *
     * @param frameBuffer the FrameBuffer to update
     * @param uByte the byte to draw
     * @param x the starting x coordinate
     * @param y the y coordinate
     * @return true if any pixels were erased, false if they were not
     */
    fun drawUByte(frameBuffer: FrameBuffer, uByte: UByte, x: Int, y: Int): Boolean {
        val i = x % SCREEN_WIDTH
        val j = y % SCREEN_HEIGHT
        val row = frameBuffer[j]
        val uLong = reverseBits(uByte).toULong()
        val mainMask = uLong shl i
        val wrapMask =
            if ((i + 8) > SCREEN_WIDTH) {
                uLong shr (SCREEN_WIDTH - i)
            } else {
                0uL
            }
        val updatedRow = row xor mainMask xor wrapMask

        frameBuffer[j] = updatedRow

        return (row and updatedRow.inv()) != 0uL
    }

    /**
     * Clear the FrameBuffer.
     *
     * @param frameBuffer the FrameBuffer to clear.
     */
    fun clear(frameBuffer: FrameBuffer) {
        frameBuffer.fill(0uL)
    }

    private fun reverseBits(uByte: UByte): UByte {
        var b = uByte.toInt()

        b = ((b and 0xF0) shr 4) or ((b and 0x0F) shl 4)  // swap nibbles
        b = ((b and 0xCC) shr 2) or ((b and 0x33) shl 2)  // swap pairs
        b = ((b and 0xAA) shr 1) or ((b and 0x55) shl 1)  // swap bits

        return b.toUByte()
    }
}