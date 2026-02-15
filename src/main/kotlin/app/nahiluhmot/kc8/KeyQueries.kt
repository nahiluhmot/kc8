package app.nahiluhmot.kc8


/**
 * Queries against a KeySet.
 */
object KeyQueries {
    private const val ZERO: UShort = 0u

    /**
     * Add a key to the set.
     *
     * @param keySet the starting KeySet
     * @param key the new Key
     * @return the updated KeySet
     */
    fun addKey(keySet: KeySet, key: Key): KeySet =
        keySet or (1 shl key.toInt()).toUShort()

    /**
     * Remove a key to the set.
     *
     * @param keySet the starting KeySet
     * @param key the Key to remove
     * @return the updated KeySet
     */
    fun removeKey(keySet: KeySet, key: Key): KeySet =
        keySet and (1 shl key.toInt()).inv().toUShort()

    /**
     * Test if a Key is pressed.
     *
     * @param keySet the KeySet
     * @param key the Key
     * @return true if key is in keySet, false otherwise
     */
    fun isKeyPressed(keySet: KeySet, key: Key): Boolean {
        return (keySet and (1 shl key.toInt()).toUShort()) != ZERO
    }

    /**
     * Get the currently pressed key. If multiple keys are pressed, the "lowest" wins (by byte value).
     *
     * @param keySet the KeySet
     * @return the currently pressed Key; null if no Key is pressed
     */
    fun getPressedKey(keySet: KeySet): Key? =
        if (keySet == ZERO) {
            null
        } else {
            keySet.countTrailingZeroBits().toUByte()
        }
}