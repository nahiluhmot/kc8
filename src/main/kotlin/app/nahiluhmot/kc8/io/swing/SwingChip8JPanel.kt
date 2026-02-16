package app.nahiluhmot.kc8.io.swing

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import app.nahiluhmot.kc8.Constants.SCREEN_WIDTH
import app.nahiluhmot.kc8.data.FrameBuffer
import app.nahiluhmot.kc8.data.FrameBufferQueries
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JPanel

@OptIn(ExperimentalUnsignedTypes::class)
/***
 * JPanel for the rendered game.
 *
 * @param scale the scale for rendering
 */
class SwingChip8JPanel(scale: Int) : JPanel() {
    private val scaledWidth: Int = scale * SCREEN_WIDTH
    private val scaledHeight: Int = scale * SCREEN_HEIGHT
    private val imageBuffer = BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_BYTE_BINARY)

    companion object {
        private const val ZERO: UByte = 0u
    }

    init {
        this.preferredSize = Dimension(scaledWidth, scaledHeight)
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        if (g == null) return

        val g2d = g as Graphics2D

        g2d.drawImage(imageBuffer, 0, 0, scaledWidth, scaledHeight, null)
    }

    /**
     * Render the frame.
     *
     * @param frameBuffer the image to display.
     */
    fun updateDisplay(frameBuffer: FrameBuffer) {
        val raster = imageBuffer.raster

        for (x in 0..<SCREEN_WIDTH) {
            for (y in 0..<SCREEN_HEIGHT) {
                val pixel = if (FrameBufferQueries.isPixelOn(frameBuffer, x, y)) 1 else 0

                raster.setSample(x, y, 0, pixel)
            }
        }

        repaint()
    }
}