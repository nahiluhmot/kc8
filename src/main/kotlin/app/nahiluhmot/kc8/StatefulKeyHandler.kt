package app.nahiluhmot.kc8

/**
 * Handles updates to the state's currently pressed keys.
 */
class StatefulKeyHandler(val state: State) : KeyHandler {
    override fun onKeyDown(key: Key) {
        state.keySet = KeySetQueries.addKey(state.keySet, key)
    }

    override fun onKeyUp(key: Key) {
        state.keySet = KeySetQueries.removeKey(state.keySet, key)
    }
}