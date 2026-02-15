package app.nahiluhmot.kc8

/**
 * Keeps track of currently pressed keys.
 */
class StatefulKeyHandler : KeyHandler {
    private val pressedKeys: MutableSet<UByte> = HashSet()

    override fun onKeyDown(key: UByte) {
        pressedKeys.add(key)
    }

    override fun onKeyUp(key: UByte) {
        pressedKeys.remove(key)
    }

    /**
     * Test whether the given key is pressed.
     *
     * @param key the key to test
     */
    fun isKeyPressed(key: UByte) =
        pressedKeys.contains(key)

    /**
     * Get a currently pressed key; null if no keys are pressed.
     */
    fun getPressedKey(): UByte? =
        if (pressedKeys.isEmpty()) {
            null
        } else {
            pressedKeys.last()
        }
}