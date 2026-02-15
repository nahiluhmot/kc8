package app.nahiluhmot.kc8.swing

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import app.nahiluhmot.kc8.Constants.SCREEN_WIDTH
import app.nahiluhmot.kc8.FrameBuffer
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JPanel

@OptIn(ExperimentalUnsignedTypes::class)
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

    fun updateDisplay(frameBuffer: FrameBuffer) {
        val raster = imageBuffer.raster

        for (row in 0..<SCREEN_HEIGHT) {
            val rowArray = frameBuffer[row]

            for (column in 0..<SCREEN_WIDTH) {
                val cell = rowArray[column]
                val pixel = if (cell) 1 else 0

                raster.setSample(column, row, 0, pixel)
            }
        }

        repaint()
    }
}