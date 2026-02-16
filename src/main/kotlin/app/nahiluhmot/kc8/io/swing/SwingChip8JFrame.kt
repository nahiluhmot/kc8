package app.nahiluhmot.kc8.io.swing

import app.nahiluhmot.kc8.Constants.APPLICATION_NAME
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Frame which sets up a single Panel and KeyListener.
 *
 * @param panel JPanel contained in this frame.
 * @param keyListener the KeyListener which handles events.
 */
class SwingChip8JFrame(panel: JPanel, keyListener: KeyListener) : JFrame(APPLICATION_NAME) {
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        add(panel)
        addKeyListener(keyListener)
        pack()
        setLocationRelativeTo(null)
        isFocusable = true
        requestFocus()
        isVisible = true
    }
}