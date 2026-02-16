package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import kotlin.test.*

@OptIn(ExperimentalUnsignedTypes::class)
class FrameBufferMutationsTest {
    private val frameBuffer = ULongArray(SCREEN_HEIGHT)

    @BeforeTest
    fun setup() {
        frameBuffer.fill(0uL)
    }

    @Test
    fun testDrawUByteBlank() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0x08u, 0, 0))


        for (i in 0 until frameBuffer.size) {
            if (i == 0) {
                assertEquals(0x10uL, frameBuffer[i], "Unexpected value at frameBuffer[$i]")
            } else {
                assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteXOverflow() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0xFFu, 65, 5))

        for (i in 0 until frameBuffer.size) {
            if (i == 5) {
                assertEquals(0xFFuL shl 1, frameBuffer[i], "Unexpected value at frameBuffer[$i]")
            } else {
                assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteYOverflow() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0xFFu, 5, 70))

        for (i in 0 until frameBuffer.size) {
            if (i == 6) {
                assertEquals(0xFFuL shl 5, frameBuffer[i], "Unexpected value at frameBuffer[$i]")
            } else {
                assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteWrap() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0xFFu, 60, 10))

        for (i in 0 until frameBuffer.size) {
            if (i == 10) {
                assertEquals(0xF00000000000000FuL, frameBuffer[i], "Unexpected value at frameBuffer[$i]")
            } else {
                assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteIndependentWrites() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0x08u, 0, 5))
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0x80u, 20, 8))

        for (i in 0 until frameBuffer.size) {
            when (i) {
                5 -> assertEquals(0x10uL, frameBuffer[i], "Unexpected value at frameBuffer[$i]")
                8 -> assertEquals(0x01uL shl 20, frameBuffer[i], "Unexpected value at frameBuffer[$i]")
                else -> assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteFullOverwrite() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0x80u, 20, 8))
        assertTrue(FrameBufferMutations.drawUByte(frameBuffer, 0x80u, 20, 8))

        for (i in 0 until frameBuffer.size) {
            assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
        }
    }

    @Test
    fun testDrawUByteAddPixels() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0x30u, 50, 20))
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0x40u, 50, 20))

        for (i in 0 until frameBuffer.size) {
            if (i == 20) {
                assertEquals(0x0EuL shl 50, frameBuffer[i], "Unexpected value at frameBuffer[$i]")
            } else {
                assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteRemovePixels() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0xFFu, 32, 31))
        assertTrue(FrameBufferMutations.drawUByte(frameBuffer, 0xAAu, 32, 31))

        for (i in 0 until frameBuffer.size) {
            if (i == 31) {
                assertEquals(
                    (0xAAuL shl 32).toHexString(),
                    frameBuffer[i].toHexString(),
                    "Unexpected value at frameBuffer[$i]"
                )
            } else {
                assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
            }
        }
    }

    @Test
    fun testClear() {
        assertFalse(FrameBufferMutations.drawUByte(frameBuffer, 0x7Eu, 20, 8))

        FrameBufferMutations.clear(frameBuffer)

        for (i in 0 until frameBuffer.size) {
            assertEquals(0uL, frameBuffer[i], "Expected frameBuffer[$i] to be 0")
        }
    }
}