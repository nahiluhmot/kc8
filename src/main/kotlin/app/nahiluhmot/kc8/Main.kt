@file:OptIn(ExperimentalUnsignedTypes::class)

package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.data.FrameBufferMutations
import app.nahiluhmot.kc8.data.State
import app.nahiluhmot.kc8.io.Loader
import app.nahiluhmot.kc8.io.StatefulKeyHandler
import app.nahiluhmot.kc8.io.clip.ClipAudioDriver
import app.nahiluhmot.kc8.io.swing.SwingDisplayDriver

/**
 * Application entrypoint.
 */
fun main() {
    val state = State()
    val uiDriver = SwingDisplayDriver()
    val audioDriver = ClipAudioDriver()
    val keyHandler = StatefulKeyHandler(state)

    audioDriver.startUp()
    uiDriver.startUp(keyHandler)

    Loader.loadFont(state)

    Thread.sleep(1000)

    drawPicture1(state)
    uiDriver.render(state.frameBuffer)
    audioDriver.startBeep()
    Thread.sleep(1000)

    drawPicture2(state)
    uiDriver.render(state.frameBuffer)
    audioDriver.stopBeep()
    Thread.sleep(1000)

    drawPicture2(state)
    uiDriver.render(state.frameBuffer)
    audioDriver.startBeep()
    Thread.sleep(1000)

    drawPicture1(state)
    uiDriver.render(state.frameBuffer)
    audioDriver.stopBeep()
    Thread.sleep(1000)

    audioDriver.shutDown()
    uiDriver.shutDown()
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
        FrameBufferMutations.drawUByte(
            state.frameBuffer,
            state.memory[addr + i],
            x,
            y + i,
        )
    }
}