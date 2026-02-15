package app.nahiluhmot.kc8

/**
 * Application constants.
 */
object Constants {
    // State

    const val MEMORY_BYTES = 4096
    const val FRAME_BUFFER_BYTES = 2048
    const val REGISTER_BYTES = 16
    const val STACK_BYTES = 64
    const val INITIAL_STACK_POINTER: StackPointer = 0u
    const val INITIAL_DELAY_TIMER: DelayTimer = 0u
    const val INITIAL_SOUND_TIMER: SoundTimer = 0u
    const val INITIAL_INDEX_REGISTER: IndexRegister = 0u
    const val INITIAL_PROGRAM_COUNTER: ProgramCounter = 0x200u
    const val DEFAULT_PRESSED_KEYS: KeySet = 0u

    // Font

    const val FONT_PATH = "/font.c8"

    // Screen

    const val SCREEN_WIDTH = 64
    const val SCREEN_HEIGHT = 32
    const val DEFAULT_SCALE = 10
}