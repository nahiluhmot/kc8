package app.nahiluhmot.kc8.swing

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.Key
import app.nahiluhmot.kc8.KeyHandler
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

/**
 * Listens to keyboard events and propagates relevant events to the KeyHandler.
 *
 * @param keyHandler the KeyHandler to which events are propagated.
 */
class SwingChip8KeyListener(val keyHandler: KeyHandler) : KeyListener {
    companion object {
        private val KEY_CODE_MAP: Map<Int, Key> = mapOf(
            KeyEvent.VK_0 to Constants.KEY_0,
            KeyEvent.VK_1 to Constants.KEY_1,
            KeyEvent.VK_2 to Constants.KEY_2,
            KeyEvent.VK_3 to Constants.KEY_3,
            KeyEvent.VK_4 to Constants.KEY_4,
            KeyEvent.VK_5 to Constants.KEY_5,
            KeyEvent.VK_6 to Constants.KEY_6,
            KeyEvent.VK_7 to Constants.KEY_7,
            KeyEvent.VK_8 to Constants.KEY_8,
            KeyEvent.VK_9 to Constants.KEY_9,
            KeyEvent.VK_A to Constants.KEY_A,
            KeyEvent.VK_B to Constants.KEY_B,
            KeyEvent.VK_C to Constants.KEY_C,
            KeyEvent.VK_D to Constants.KEY_D,
            KeyEvent.VK_E to Constants.KEY_E,
            KeyEvent.VK_F to Constants.KEY_F,
        )
    }

    override fun keyTyped(e: KeyEvent?) {
        // no-op
    }

    override fun keyPressed(e: KeyEvent?) {
        KEY_CODE_MAP[e?.keyCode]?.let { keyHandler.onKeyDown(it) }
    }

    override fun keyReleased(e: KeyEvent?) {
        KEY_CODE_MAP[e?.keyCode]?.let { keyHandler.onKeyUp(it) }
    }
}