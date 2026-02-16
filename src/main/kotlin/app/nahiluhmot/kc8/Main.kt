@file:OptIn(ExperimentalUnsignedTypes::class)

package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.swing.SwingIO

/**
 * Application entrypoint.
 */
fun main() {
    val state = State()
    val io = SwingIO()
    val keyHandler = StatefulKeyHandler(state)

    io.startUp(keyHandler)

    FontLoader.loadFont(state)

    Thread.sleep(1000)

    while (true) {
        drawPicture1(state)
        io.render(state.display)
        Thread.sleep(1000)

        drawPicture2(state)
        io.render(state.display)
        Thread.sleep(1000)

        drawPicture2(state)
        io.render(state.display)
        Thread.sleep(1000)

        drawPicture1(state)
        io.render(state.display)
        Thread.sleep(1000)
    }
}

private fun drawPicture1(state: State) {
    for (i in 0..7) {
        drawSprite(state, i * 5, 5 * (i + 1), 5, 5)
    }
}

private fun drawPicture2(state: State) {
    for (i in 8..15) {
        drawSprite(state, i * 5, (5 * i) - 20, 21, 5)
    }
}

private fun drawSprite(state: State, addr: Int, x: Int, y: Int, height: Int) {
    for (i in 0 until height) {
        DisplayMutations.drawUByte(
            state.display,
            state.memory[addr + i],
            x,
            y + i,
        )
    }
}