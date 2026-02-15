package app.nahiluhmot.kc8.swing

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.FrameBuffer
import app.nahiluhmot.kc8.IO
import app.nahiluhmot.kc8.KeyHandler
import javax.swing.SwingUtilities

@OptIn(ExperimentalUnsignedTypes::class)
class SwingIO(val scale: Int = Constants.DEFAULT_SCALE) : IO {
    private lateinit var frame: SwingChip8JFrame

    override fun startUp(keyHandler: KeyHandler) {
        SwingUtilities.invokeLater {
            frame = SwingChip8JFrame(scale, keyHandler)
        }
    }

    override fun shutDown() {
        SwingUtilities.invokeLater {
            frame.dispose()
        }
    }

    override fun render(frameBuffer: FrameBuffer) {
        SwingUtilities.invokeLater {
            frame.updateDisplay(frameBuffer)
        }
    }

    override fun startSound() {
        TODO("Not yet implemented")
    }

    override fun stopSound() {
        TODO("Not yet implemented")
    }
}