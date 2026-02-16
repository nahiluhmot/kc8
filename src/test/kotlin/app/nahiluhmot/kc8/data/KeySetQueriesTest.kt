package app.nahiluhmot.kc8.data

import app.nahiluhmot.kc8.Constants
import kotlin.test.*

class KeySetQueriesTest {
    @Test
    fun testAddKeyToEmpty() {
        for (key in Constants.KEY_0..Constants.KEY_F) {
            val expected = (1 shl key.toInt()).toUShort()

            assertEquals(
                expected,
                KeySetQueries.addKey(0x0u, key.toUByte()),
                "Expected setting $key to result in $expected"
            )
        }
    }

    @Test
    fun testAddAllKeys() {
        var keySet: KeySet = 0x0u

        for (key in Constants.KEY_0..Constants.KEY_F) {
            keySet = KeySetQueries.addKey(keySet, key.toUByte())
        }

        assertEquals(0x0u.toUShort().inv(), keySet)
    }

    @Test
    fun testRemoveKeyNotAdded() {
        assertEquals(0x0u, KeySetQueries.removeKey(0x0u, Constants.KEY_F))
    }

    @Test
    fun testRemoveAddedKey() {
        var keySet: KeySet = 0b1000010010000u

        keySet = KeySetQueries.removeKey(keySet, Constants.KEY_7)

        assertEquals(0b1000000010000u, keySet)
    }

    @Test
    fun testIsKeySet() {
        val keySet: KeySet = 0b1001u

        assertTrue(KeySetQueries.isKeyPressed(keySet, Constants.KEY_0))
        assertFalse(KeySetQueries.isKeyPressed(keySet, Constants.KEY_1))
        assertFalse(KeySetQueries.isKeyPressed(keySet, Constants.KEY_2))
        assertTrue(KeySetQueries.isKeyPressed(keySet, Constants.KEY_3))
        assertFalse(KeySetQueries.isKeyPressed(keySet, Constants.KEY_4))
    }

    @Test
    fun testGetPressedKey() {
        assertNull(KeySetQueries.getPressedKey(0b0u))
        assertEquals(Constants.KEY_0, KeySetQueries.getPressedKey(0b1u))
        assertEquals(Constants.KEY_1, KeySetQueries.getPressedKey(0b10u))
        assertEquals(Constants.KEY_0, KeySetQueries.getPressedKey(0b11u))
    }
}