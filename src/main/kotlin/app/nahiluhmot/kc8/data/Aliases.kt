@file:OptIn(ExperimentalUnsignedTypes::class)

package app.nahiluhmot.kc8.data

/**
 * Block of memory for fonts, programs, and general program usage.
 */
typealias Memory = UByteArray

/**
 * Addresses are used to index the Memory Array.
 */
typealias Address = Int

/**
 * The display is 64x32. Using a ULongArray, we can represent each row as an element in the Array.
 * ULongs are convenient for writing sprites using bitwise operations.
 */
typealias FrameBuffer = ULongArray

/**
 * Op code registers.
 */
typealias Registers = UByteArray

/**
 * A register is used to index the Registers Array.
 */
typealias Register = Int

/**
 * Program stack.
 */
typealias Stack = UByteArray

/**
 * Pointer to the current stack.
 */
typealias StackPointer = Int

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
 * Chip-8 supports key-presses on 0-9, A-F.
 */
typealias Key = UByte

/**
 * To be efficient, we can store the currently pressed
 * keys as an unsigned short. See KeyQueries for details on how these keys are updated and queried.
 */
typealias KeySet = UShort