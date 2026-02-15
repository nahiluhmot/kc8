package app.nahiluhmot.kc8

import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull

@OptIn(ExperimentalUnsignedTypes::class)
class StatefulKeyHandlerTest {
    @Test
    fun testKeyPressed() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyDown(0x4u)

        assertPressedKeys(state.pressedKeys, setOf(0x4u))
    }

    @Test
    fun testKeyReleased() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyUp(0xEu)

        assertNoPressedKeys(state.pressedKeys)
    }

    @Test
    fun testKeyPressedAndReleased() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyDown(0x7u)
        keyHandler.onKeyUp(0x7u)

        assertNoPressedKeys(state.pressedKeys)
    }

    @Test
    fun testMultipleKeysPressed() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyDown(0x1u)
        keyHandler.onKeyDown(0xBu)

        assertPressedKeys(state.pressedKeys, setOf(0x1u, 0xBu))
    }

    @Test
    fun testPressReleaseAllKeys() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        assertNoPressedKeys(state.pressedKeys)

        for (key in 0x0u..0xFu) {
            keyHandler.onKeyDown(key.toUByte())
            assertPressedKeys(state.pressedKeys, setOf(key.toUByte()))
            keyHandler.onKeyUp(key.toUByte())
            assertNoPressedKeys(state.pressedKeys)
        }
    }

    @Test
    fun testComplex() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyDown(0x2u)
        keyHandler.onKeyDown(0x3u)
        keyHandler.onKeyUp(0x2u)
        keyHandler.onKeyUp(0x4u)
        keyHandler.onKeyDown(0x5u)

        assertPressedKeys(state.pressedKeys, setOf(0x3u, 0x5u))
    }

    private fun assertNoPressedKeys(pressedKeys: PressedKeys) {
        assertPressedKeys(pressedKeys, emptySet())
    }

    private fun assertPressedKeys(pressedKeys: PressedKeys, expectedKeys: Collection<UByte>) {
        for (key in 0x0u..0xFu) {
            if (expectedKeys.contains(key.toUByte())) {
                assertTrue(KeyQueries.isKeyPressed(pressedKeys, key.toUByte()), "Expected $key to be pressed")
            } else {
                assertFalse(
                    KeyQueries.isKeyPressed(pressedKeys, key.toUByte()),
                    "Expected $key to not be pressed"
                )
            }
        }

        val pressedKey = KeyQueries.getPressedKey(pressedKeys)

        if (expectedKeys.isEmpty()) {
            assertNull(pressedKey)
        } else {
            assertTrue(
                expectedKeys.contains(pressedKey),
                "Expected the pressed to be in the set: $expectedKeys, got: $pressedKey"
            )
        }
    }
}