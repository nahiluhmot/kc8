package app.nahiluhmot.kc8

/**
 * Interface for keyboard input handlers. Valid keys are 0-9, A-F, represented as bytes.
 */
interface KeyHandler {
    /**
     * Handle a key down event.
     *
     * @param key the key that was pressed
     */
    fun onKeyDown(key: UByte)

    /**
     * Handle a key up event.
     *
     * @param key the key that was released
     */
    fun onKeyUp(key: UByte)
}