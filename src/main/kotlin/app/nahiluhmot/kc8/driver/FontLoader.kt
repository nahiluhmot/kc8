package app.nahiluhmot.kc8.driver

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.State

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Handles font loading from a resource file.
 */
object FontLoader {
    /**
     * Loads the default font from a resource file and writes it to the state.
     *
     * @param state the state to which the font should be written
     * @throws IllegalStateException when the font cannot be loaded.
     */
    fun loadFont(state: State) {
        val stream = this.javaClass.getResourceAsStream(Constants.FONT_PATH)
        val bytes = stream?.use { it.readBytes().toUByteArray() } ?: throw IllegalStateException("Could not load font")

        bytes.copyInto(state.memory)
    }
}