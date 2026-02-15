package app.nahiluhmot.kc8

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalUnsignedTypes::class)
class FontLoaderTest {
    @Test
    fun testLoadFont() {
        val state = State()

        assertEquals(0x0u, state.memory[0])

        FontLoader.loadFont(state)

        assertEquals(0xF0u, state.memory[0])

        for (i in Constants.INITIAL_PROGRAM_COUNTER.toInt()..<state.memory.size) {
            assertEquals(state.memory[i], 0u, "memory[$i] != 0")
        }
    }
}