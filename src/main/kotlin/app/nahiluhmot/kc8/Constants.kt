package app.nahiluhmot.kc8

object Constants {
    // Memory
    const val MEMORY_BYTES = 4096
    const val FRAME_BUFFER_BYTES = 2048
    const val REGISTER_BYTES = 16
    const val STACK_BYTES = 64
    const val INITIAL_STACK_POINTER: UByte = 0u
    const val INITIAL_DELAY_TIMER: UByte = 0u
    const val INITIAL_SOUND_TIMER: UByte = 0u
    const val INITIAL_INDEX_REGISTER: UShort = 0u
    const val INITIAL_PROGRAM_COUNTER: UShort = 0x200u

    // Font
    const val FONT_PATH = "/font.c8"
}