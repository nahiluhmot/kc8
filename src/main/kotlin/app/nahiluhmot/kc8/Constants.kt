package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.data.*

/**
 * Application constants.
 */
object Constants {
    // Application

    const val APPLICATION_NAME = "KC8"

    // Font

    const val FONT_PATH = "/font.ch8"

    // Screen

    const val SCREEN_WIDTH = 64
    const val SCREEN_HEIGHT = 32
    const val DEFAULT_SCALE = 10

    // State

    const val MEMORY_BYTES = 4096
    const val REGISTER_BYTES = 16
    const val STACK_BYTES = 64
    const val INITIAL_STACK_POINTER: StackPointer = 0
    const val INITIAL_DELAY_TIMER: DelayTimer = 0u
    const val INITIAL_SOUND_TIMER: SoundTimer = 0u
    const val INITIAL_INDEX_REGISTER: IndexRegister = 0
    const val INITIAL_PROGRAM_COUNTER: ProgramCounter = 0x200
    const val DEFAULT_PRESSED_KEYS: KeySet = 0u

    // Keys
    const val KEY_0: Key = 0x0u
    const val KEY_1: Key = 0x1u
    const val KEY_2: Key = 0x2u
    const val KEY_3: Key = 0x3u
    const val KEY_4: Key = 0x4u
    const val KEY_5: Key = 0x5u
    const val KEY_6: Key = 0x6u
    const val KEY_7: Key = 0x7u
    const val KEY_8: Key = 0x8u
    const val KEY_9: Key = 0x9u
    const val KEY_A: Key = 0xAu
    const val KEY_B: Key = 0xBu
    const val KEY_C: Key = 0xCu
    const val KEY_D: Key = 0xDu
    const val KEY_E: Key = 0xEu
    const val KEY_F: Key = 0xFu
}