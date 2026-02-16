package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import kotlin.test.*

@OptIn(ExperimentalUnsignedTypes::class)
class DisplayMutationsTest {
    private val display = ULongArray(SCREEN_HEIGHT)

    @BeforeTest
    fun setup() {
        display.fill(0uL)
    }

    @Test
    fun testDrawUByteBlank() {
        assertFalse(DisplayMutations.drawUByte(display, 0x08u, 0, 0))


        for (i in 0 until display.size) {
            if (i == 0) {
                assertEquals(0x10uL, display[i], "Unexpected value at display[$i]")
            } else {
                assertEquals(0uL, display[i], "Expected display[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteXOverflow() {
        assertFalse(DisplayMutations.drawUByte(display, 0xFFu, 65, 5))

        for (i in 0 until display.size) {
            if (i == 5) {
                assertEquals(0xFFuL shl 1, display[i], "Unexpected value at display[$i]")
            } else {
                assertEquals(0uL, display[i], "Expected display[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteYOverflow() {
        assertFalse(DisplayMutations.drawUByte(display, 0xFFu, 5, 70))

        for (i in 0 until display.size) {
            if (i == 6) {
                assertEquals(0xFFuL shl 5, display[i], "Unexpected value at display[$i]")
            } else {
                assertEquals(0uL, display[i], "Expected display[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteWrap() {
        assertFalse(DisplayMutations.drawUByte(display, 0xFFu, 60, 10))

        for (i in 0 until display.size) {
            if (i == 10) {
                assertEquals(0xF00000000000000FuL, display[i], "Unexpected value at display[$i]")
            } else {
                assertEquals(0uL, display[i], "Expected display[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteIndependentWrites() {
        assertFalse(DisplayMutations.drawUByte(display, 0x08u, 0, 5))
        assertFalse(DisplayMutations.drawUByte(display, 0x80u, 20, 8))

        for (i in 0 until display.size) {
            when (i) {
                5 -> assertEquals(0x10uL, display[i], "Unexpected value at display[$i]")
                8 -> assertEquals(0x01uL shl 20, display[i], "Unexpected value at display[$i]")
                else -> assertEquals(0uL, display[i], "Expected display[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteFullOverwrite() {
        assertFalse(DisplayMutations.drawUByte(display, 0x80u, 20, 8))
        assertTrue(DisplayMutations.drawUByte(display, 0x80u, 20, 8))

        for (i in 0 until display.size) {
            assertEquals(0uL, display[i], "Expected display[$i] to be 0")
        }
    }

    @Test
    fun testDrawUByteAddPixels() {
        assertFalse(DisplayMutations.drawUByte(display, 0x30u, 50, 20))
        assertFalse(DisplayMutations.drawUByte(display, 0x40u, 50, 20))

        for (i in 0 until display.size) {
            if (i == 20) {
                assertEquals(0x0EuL shl 50, display[i], "Unexpected value at display[$i]")
            } else {
                assertEquals(0uL, display[i], "Expected display[$i] to be 0")
            }
        }
    }

    @Test
    fun testDrawUByteRemovePixels() {
        assertFalse(DisplayMutations.drawUByte(display, 0xFFu, 32, 31))
        assertTrue(DisplayMutations.drawUByte(display, 0xAAu, 32, 31))

        for (i in 0 until display.size) {
            if (i == 31) {
                assertEquals((0xAAuL shl 32).toHexString(), display[i].toHexString(), "Unexpected value at display[$i]")
            } else {
                assertEquals(0uL, display[i], "Expected display[$i] to be 0")
            }
        }
    }

    @Test
    fun testClear() {
        assertFalse(DisplayMutations.drawUByte(display, 0x7Eu, 20, 8))

        DisplayMutations.clear(display)

        for (i in 0 until display.size) {
            assertEquals(0uL, display[i], "Expected display[$i] to be 0")
        }
    }
}