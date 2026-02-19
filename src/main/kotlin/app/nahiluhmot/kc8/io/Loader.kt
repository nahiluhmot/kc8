package app.nahiluhmot.kc8.io

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.data.State
import java.io.File

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Handles font loading from a resource file.
 */
object Loader {
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

    /**
     * Loads a program into the state, starting at the initial program counter.
     *
     * @param file the file which contains the program
     * @param state the state to which the program should be written
     */
    fun loadProgram(file: File, state: State) {
        val bytes = file.readBytes().toUByteArray()

        bytes.copyInto(state.memory, Constants.INITIAL_PROGRAM_COUNTER)
    }
}