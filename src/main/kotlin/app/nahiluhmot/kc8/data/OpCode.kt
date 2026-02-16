package app.nahiluhmot.kc8.data

/**
 * Sealed interface for op codes. See this document[1] for the op code specification.
 *
 * [1] https://www.cs.columbia.edu/~sedwards/classes/2016/4840-spring/designs/Chip8.pdf
 */
sealed interface OpCode {
    // 0nnn
    data class SysCall(val addr: Address) : OpCode

    // 00E0
    data object ClearScreen : OpCode

    // 00EE
    data object Return : OpCode

    // 1nnn
    data class Jump(val addr: Address) : OpCode

    // 2nnn
    data class Call(val addr: Address) : OpCode

    // 3xkk
    data class SkipEqual(val reg: Register, val byte: UByte) : OpCode

    // 4xkk
    data class SkipNotEqual(val reg: Register, val byte: UByte) : OpCode

    // 5xy0
    data class SkipRegEqual(val left: Register, val right: Register) : OpCode

    // 6xkk
    data class Load(val reg: Register, val byte: UByte) : OpCode

    // 7xkk
    data class Add(val reg: Register, val byte: UByte) : OpCode

    // 8xy0
    data class RegLoad(val dst: Register, val src: Register) : OpCode

    // 8xy1
    data class RegOr(val dst: Register, val src: Register) : OpCode

    // 8xy2
    data class RegAnd(val dst: Register, val src: Register) : OpCode

    // 8xy3
    data class RegXor(val dst: Register, val src: Register) : OpCode

    // 8xy4
    data class RegAdd(val dst: Register, val src: Register) : OpCode

    // 8xy5
    data class RegSub(val dst: Register, val src: Register) : OpCode

    // 8xy6
    data class ShiftRight(val reg: Register) : OpCode

    // 8xy7
    data class RegSubReverse(val dst: Register, val src: Register) : OpCode

    // 8xyE
    data class ShiftLeft(val reg: Register) : OpCode

    // 9xy0
    data class SkipRegNotEqual(val left: Register, val right: Register) : OpCode

    // Annn
    data class SetIndex(val addr: Address) : OpCode

    // Bnnn
    data class Reg0Jump(val addr: Address) : OpCode

    // Cxkk
    data class Random(val reg: Register, val byte: UByte) : OpCode

    // Dxyn
    data class Draw(val x: Register, val y: Register, val height: Int) : OpCode

    // Ex9E
    data class SkipKey(val reg: Register) : OpCode

    // ExA1
    data class SkipNotKey(val reg: Register) : OpCode

    // Fx07
    data class SetToDelay(val reg: Register) : OpCode

    // Fx0A
    data class WaitForKey(val reg: Register) : OpCode

    // Fx15
    data class SetDelay(val reg: Register) : OpCode

    // Fx18
    data class SetSound(val reg: Register) : OpCode

    // Fx1E
    data class AddIndex(val reg: Register) : OpCode

    // Fx29
    data class LoadSprite(val reg: Register) : OpCode

    // Fx33
    data class StoreBcd(val reg: Register) : OpCode

    // Fx55
    data class StoreRegisters(val reg: Register) : OpCode

    // Fx65
    data class LoadRegisters(val reg: Register) : OpCode
}