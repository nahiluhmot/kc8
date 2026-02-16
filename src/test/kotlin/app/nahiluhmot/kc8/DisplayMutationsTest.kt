package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

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

        assertEquals(0x10uL, display[0])

        for (i in 1 until display.size) {
            assertEquals(0uL, display[i], "Expected display[$i] to be 0")
        }
    }

    @Test
    fun testDrawUByteOverflow() {
        TODO()
        // assertFalse(DisplayMutations.drawUByte(display, 0x08u, 0, 5))
    }

    @Test
    fun testDrawUByteMultipleWrites() {
        TODO()
        // assertFalse(DisplayMutations.drawUByte(display, 0x08u, 0, 5))
        // assertFalse(DisplayMutations.drawUByte(display, 0x08u, 20, 8))
    }

    @Test
    fun testDrawUByteFullOverwrite() {
        TODO()
    }

    @Test
    fun testDrawUByteAddPixels() {
        TODO()
    }

    @Test
    fun testDrawUByteRemovePixels() {
        TODO()
    }
}