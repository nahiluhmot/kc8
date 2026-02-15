package app.nahiluhmot.kc8

private val ZERO: UShort = 0u

/**
 * Handles updates to the state's currently pressed keys.
 */
class StatefulKeyHandler(val state: State) : KeyHandler {
    override fun onKeyDown(key: UByte) {
        state.pressedKeys = KeyQueries.addKey(state.pressedKeys, key)
    }

    override fun onKeyUp(key: UByte) {
        state.pressedKeys = KeyQueries.removeKey(state.pressedKeys, key)
    }
}