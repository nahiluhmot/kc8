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

        keyHandler.onKeyDown(Constants.KEY_4)

        assertPressedKeys(state.keySet, setOf(Constants.KEY_4))
    }

    @Test
    fun testKeyReleased() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyUp(Constants.KEY_E)

        assertNoPressedKeys(state.keySet)
    }

    @Test
    fun testKeyPressedAndReleased() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyDown(Constants.KEY_7)
        keyHandler.onKeyUp(Constants.KEY_7)

        assertNoPressedKeys(state.keySet)
    }

    @Test
    fun testMultipleKeysPressed() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyDown(Constants.KEY_1)
        keyHandler.onKeyDown(Constants.KEY_B)

        assertPressedKeys(state.keySet, setOf(Constants.KEY_1, Constants.KEY_B))
    }

    @Test
    fun testPressReleaseAllKeys() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        assertNoPressedKeys(state.keySet)

        for (key in Constants.KEY_0..Constants.KEY_F) {
            keyHandler.onKeyDown(key.toUByte())
            assertPressedKeys(state.keySet, setOf(key.toUByte()))
            keyHandler.onKeyUp(key.toUByte())
            assertNoPressedKeys(state.keySet)
        }
    }

    @Test
    fun testComplex() {
        val state = State()
        val keyHandler = StatefulKeyHandler(state)

        keyHandler.onKeyDown(Constants.KEY_2)
        keyHandler.onKeyDown(Constants.KEY_3)
        keyHandler.onKeyUp(Constants.KEY_2)
        keyHandler.onKeyUp(Constants.KEY_4)
        keyHandler.onKeyDown(Constants.KEY_5)

        assertPressedKeys(state.keySet, setOf(Constants.KEY_3, Constants.KEY_5))
    }

    private fun assertNoPressedKeys(keySet: KeySet) {
        assertPressedKeys(keySet, emptySet())
    }

    private fun assertPressedKeys(keySet: KeySet, expectedKeys: Collection<UByte>) {
        for (key in Constants.KEY_0..Constants.KEY_F) {
            if (expectedKeys.contains(key.toUByte())) {
                assertTrue(KeyQueries.isKeyPressed(keySet, key.toUByte()), "Expected $key to be pressed")
            } else {
                assertFalse(
                    KeyQueries.isKeyPressed(keySet, key.toUByte()),
                    "Expected $key to not be pressed"
                )
            }
        }

        val pressedKey = KeyQueries.getPressedKey(keySet)

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