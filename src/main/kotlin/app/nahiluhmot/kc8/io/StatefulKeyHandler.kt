package app.nahiluhmot.kc8.io

import app.nahiluhmot.kc8.data.Key
import app.nahiluhmot.kc8.data.KeySetQueries
import app.nahiluhmot.kc8.data.State

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