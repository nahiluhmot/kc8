package app.nahiluhmot.kc8

/**
 * Handles updates to the state's currently pressed keys.
 */
class StatefulKeyHandler(val state: State) : KeyHandler {
    override fun onKeyDown(key: Key) {
        state.keySet = KeyQueries.addKey(state.keySet, key)
    }

    override fun onKeyUp(key: Key) {
        state.keySet = KeyQueries.removeKey(state.keySet, key)
    }
}