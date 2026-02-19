package app.nahiluhmot.kc8.io

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.data.State
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalUnsignedTypes::class)
class LoaderTest {
    @Test
    fun testLoadFont() {
        val state = State()

        assertEquals(0x0u, state.memory[0])

        Loader.loadFont(state)

        assertEquals(0xF0u, state.memory[0])

        for (i in Constants.INITIAL_PROGRAM_COUNTER..<state.memory.size) {
            assertEquals(state.memory[i], 0u, "memory[$i] != 0")
        }
    }

    @Test
    fun testLoadProgram() {
        val file = File(ClassLoader.getSystemResource("tetris.ch8").toURI())
        val state = State()

        Loader.loadProgram(file, state)

        assertNotEquals(0u, state.memory[Constants.INITIAL_PROGRAM_COUNTER])
    }
}