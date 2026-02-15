package app.nahiluhmot.kc8

/**
 * Handles updates to the state's currently pressed keys.
 */
class StatefulKeyHandler(val state: State) : KeyHandler {
    override fun onKeyDown(key: UByte) {
        state.keySet = KeyQueries.addKey(state.keySet, key)
    }

    override fun onKeyUp(key: UByte) {
        state.keySet = KeyQueries.removeKey(state.keySet, key)
    }
}