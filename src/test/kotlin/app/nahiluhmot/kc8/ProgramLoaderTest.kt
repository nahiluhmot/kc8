package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.data.State
import app.nahiluhmot.kc8.io.Loader
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalUnsignedTypes::class)
class ProgramLoaderTest {
    @Test
    fun testLoadFont() {
        val state = State()

        assertEquals(0x0u, state.memory[0])

        Loader.loadFont(state)

        assertEquals(0xF0u, state.memory[0])

        for (i in Constants.INITIAL_PROGRAM_COUNTER.toInt()..<state.memory.size) {
            assertEquals(state.memory[i], 0u, "memory[$i] != 0")
        }
    }
}