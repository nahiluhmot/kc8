@file:OptIn(ExperimentalUnsignedTypes::class)

package app.nahiluhmot.kc8

/**
 * Block of memory for fonts, programs, and general program usage.
 */
typealias Memory = UByteArray

/**
 * Video RAM.
 */
typealias FrameBuffer = UByteArray

/**
 * Op code registers.
 */
typealias Registers = UByteArray

/**
 * Program stack.
 */
typealias Stack = UByteArray

/**
 * Pointer to the current stack.
 */
typealias StackPointer = UByte

/**
 * Delay timer.
 */
typealias DelayTimer = UByte

/**
 * Sound timer.
 */
typealias SoundTimer = UByte

/**
 * Index register.
 */
typealias IndexRegister = UShort

/**
 * Program counter.
 */
typealias ProgramCounter = UShort

/**
 * Chip-8 supports key-presses on 0-9, A-F. To be efficient, we can store the currently pressed
 * keys as an unsigned short. See KeyQueries for details on how these keys are updated and queried.
 */
typealias KeySet = UShort
