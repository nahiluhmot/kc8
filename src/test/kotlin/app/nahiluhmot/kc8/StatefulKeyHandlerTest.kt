package app.nahiluhmot.kc8

import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull

class StatefulKeyHandlerTest {
    @Test
    fun testNoKeyPressed() {
        val keyHandler = StatefulKeyHandler()

        assertNoPressedKeys(keyHandler)
    }

    @Test
    fun testKeyPressed() {
        val keyHandler = StatefulKeyHandler()

        keyHandler.onKeyDown(0x4u)

        assertPressedKeys(keyHandler, setOf(0x4u))
    }

    @Test
    fun testKeyReleased() {
        val keyHandler = StatefulKeyHandler()

        keyHandler.onKeyUp(0xEu)

        assertNoPressedKeys(keyHandler)
    }

    @Test
    fun testKeyPressedAndReleased() {
        val keyHandler = StatefulKeyHandler()

        keyHandler.onKeyDown(0x7u)
        keyHandler.onKeyUp(0x7u)

        assertNoPressedKeys(keyHandler)
    }

    @Test
    fun testMultipleKeysPressed() {
        val keyHandler = StatefulKeyHandler()

        keyHandler.onKeyDown(0x1u)
        keyHandler.onKeyDown(0xBu)

        assertPressedKeys(keyHandler, setOf(0x1u, 0xBu))
    }

    @Test
    fun testComplex() {
        val keyHandler = StatefulKeyHandler()

        keyHandler.onKeyDown(0x2u)
        keyHandler.onKeyDown(0x3u)
        keyHandler.onKeyUp(0x2u)
        keyHandler.onKeyUp(0x4u)
        keyHandler.onKeyDown(0x5u)

        assertPressedKeys(keyHandler, setOf(0x3u, 0x5u))
    }

    private fun assertNoPressedKeys(keyHandler: StatefulKeyHandler) {
        assertPressedKeys(keyHandler, emptySet())
    }

    private fun assertPressedKeys(keyHandler: StatefulKeyHandler, pressedKeys: Collection<UByte>) {
        for (key in 0x0u..0xFu) {
            if (pressedKeys.contains(key.toUByte())) {
                assertTrue(keyHandler.isKeyPressed(key.toUByte()), "Expected $key to be pressed")
            } else {
                assertFalse(keyHandler.isKeyPressed(key.toUByte()), "Expected $key to not be pressed")
            }
        }

        if (pressedKeys.isEmpty()) {
            assertNull(keyHandler.getPressedKey())
        } else {
            assertTrue(
                pressedKeys.contains(keyHandler.getPressedKey()),
                "Expected the pressed to be in the set: $pressedKeys"
            )
        }
    }
}