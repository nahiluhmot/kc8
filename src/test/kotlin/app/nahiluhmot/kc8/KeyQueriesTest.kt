package app.nahiluhmot.kc8

import org.junit.jupiter.api.Assertions.assertFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class KeyQueriesTest {
    @Test
    fun testAddKeyToEmpty() {
        for (key in 0x0u..0xFu) {
            val expected = (1 shl key.toInt()).toUShort()

            assertEquals(
                expected,
                KeyQueries.addKey(0x0u, key.toUByte()),
                "Expected setting $key to result in $expected"
            )
        }
    }

    @Test
    fun testAddAllKeys() {
        var keySet: KeySet = 0x0u

        for (key in 0x0u..0xFu) {
            keySet = KeyQueries.addKey(keySet, key.toUByte())
        }

        assertEquals(0x0u.toUShort().inv(), keySet)
    }

    @Test
    fun testRemoveKeyNotAdded() {
        assertEquals(0x0u, KeyQueries.removeKey(0x0u, 0xFu))
    }

    @Test
    fun testRemoveAddedKey() {
        var keySet: KeySet = 0b1000010010000u

        keySet = KeyQueries.removeKey(keySet, 0x7u)

        assertEquals(0b1000000010000u, keySet)
    }

    @Test
    fun testIsKeySet() {
        val keySet: KeySet = 0b1001u

        assertTrue(KeyQueries.isKeyPressed(keySet, 0x0u))
        assertFalse(KeyQueries.isKeyPressed(keySet, 0x1u))
        assertFalse(KeyQueries.isKeyPressed(keySet, 0x2u))
        assertTrue(KeyQueries.isKeyPressed(keySet, 0x3u))
        assertFalse(KeyQueries.isKeyPressed(keySet, 0x4u))
    }

    @Test
    fun testGetPressedKey() {
        assertNull(KeyQueries.getPressedKey(0b0u))
        assertEquals(0x0u, KeyQueries.getPressedKey(0b1u))
        assertEquals(0x1u, KeyQueries.getPressedKey(0b10u))
        assertEquals(0x0u, KeyQueries.getPressedKey(0b11u))
    }
}