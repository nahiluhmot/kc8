package app.nahiluhmot.kc8.swing

import app.nahiluhmot.kc8.FrameBuffer
import app.nahiluhmot.kc8.KeyHandler
import javax.swing.JFrame

private const val WINDOW_TITLE = "CHIP-8"

class SwingChip8JFrame(scale: Int, keyHandler: KeyHandler) : JFrame(WINDOW_TITLE) {
    private val panel: SwingChip8JPanel = SwingChip8JPanel(scale)

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        add(panel)
        addKeyListener(SwingChip8KeyListener(keyHandler))
        pack()
        setLocationRelativeTo(null)
        isFocusable = true
        requestFocus()
        isVisible = true
    }

    fun updateDisplay(frameBuffer: FrameBuffer) {
        panel.updateDisplay(frameBuffer)
    }
}