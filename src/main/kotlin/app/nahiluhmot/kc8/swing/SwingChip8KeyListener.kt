package app.nahiluhmot.kc8.swing

import app.nahiluhmot.kc8.KeyHandler
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

private val KEY_CODE_MAP: Map<Int, UByte> = mapOf(
    KeyEvent.VK_0 to 0x0u,
    KeyEvent.VK_1 to 0x1u,
    KeyEvent.VK_2 to 0x2u,
    KeyEvent.VK_3 to 0x3u,
    KeyEvent.VK_4 to 0x4u,
    KeyEvent.VK_5 to 0x5u,
    KeyEvent.VK_6 to 0x6u,
    KeyEvent.VK_7 to 0x7u,
    KeyEvent.VK_8 to 0x8u,
    KeyEvent.VK_9 to 0x9u,
    KeyEvent.VK_A to 0xAu,
    KeyEvent.VK_B to 0xBu,
    KeyEvent.VK_C to 0xCu,
    KeyEvent.VK_D to 0xDu,
    KeyEvent.VK_E to 0xEu,
    KeyEvent.VK_F to 0xFu,
)

class SwingChip8KeyListener(val keyHandler: KeyHandler) : KeyListener {
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