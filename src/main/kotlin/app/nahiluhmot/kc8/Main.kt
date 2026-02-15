package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.Constants.SCREEN_HEIGHT
import app.nahiluhmot.kc8.Constants.SCREEN_WIDTH
import app.nahiluhmot.kc8.swing.SwingIO

/**
 * Application entrypoint.
 */
fun main() {
    val state = State()
    val io = SwingIO()
    val keyHandler = StatefulKeyHandler(state)

    io.startUp(keyHandler)

    while (true) {
        for (row in 0..<SCREEN_HEIGHT) {
            for (column in 0..<SCREEN_WIDTH) {
                state.frameBuffer[row][column] = ((row + column) % 2) == 0
            }
        }

        io.render(state.frameBuffer)

        Thread.sleep(500)

        for (row in 0..<SCREEN_HEIGHT) {
            for (column in 0..<SCREEN_WIDTH) {
                state.frameBuffer[row][column] = ((row + column) % 2) != 0
            }
        }

        io.render(state.frameBuffer)

        Thread.sleep(500)
    }
}