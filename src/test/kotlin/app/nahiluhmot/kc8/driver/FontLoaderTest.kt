package app.nahiluhmot.kc8.driver

import app.nahiluhmot.kc8.State
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
    }
}