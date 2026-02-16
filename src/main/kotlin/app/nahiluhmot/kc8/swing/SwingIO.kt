package app.nahiluhmot.kc8.swing

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.FrameBuffer
import app.nahiluhmot.kc8.IO
import app.nahiluhmot.kc8.KeyHandler
import javax.swing.SwingUtilities

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Instance of the IO interface for Swing.
 *
 * @param scale the scale of the rendered image
 */
class SwingIO(val scale: Int = Constants.DEFAULT_SCALE) : IO {
    private val panel = SwingChip8JPanel(scale)
    private lateinit var frame: SwingChip8JFrame

    override fun startUp(keyHandler: KeyHandler) {
        SwingUtilities.invokeLater {
            val keyListener = SwingChip8KeyListener(keyHandler)

            frame = SwingChip8JFrame(panel, keyListener)
        }
    }

    override fun shutDown() {
        SwingUtilities.invokeLater {
            frame.dispose()
        }
    }

    override fun render(frameBuffer: FrameBuffer) {
        SwingUtilities.invokeLater {
            panel.updateDisplay(frameBuffer)
        }
    }

    override fun startSound() {
        TODO("Not yet implemented")
    }

    override fun stopSound() {
        TODO("Not yet implemented")
    }
}