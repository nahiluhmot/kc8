package app.nahiluhmot.kc8.driver

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.State

@OptIn(ExperimentalUnsignedTypes::class)
object FontLoader {
    fun loadFont(state: State) {
        val stream = this.javaClass.getResourceAsStream(Constants.FONT_PATH)
        val bytes = stream?.use { it.readBytes().toUByteArray() } ?: throw IllegalStateException("Could not load font")

        bytes.copyInto(state.memory)
    }
}