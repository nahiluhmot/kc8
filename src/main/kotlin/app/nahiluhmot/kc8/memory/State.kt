package app.nahiluhmot.kc8.memory

import app.nahiluhmot.kc8.Constants

class State(
    val memory: ByteArray = ByteArray(Constants.MEMORY_BYTES),
    val frameBuffer: ByteArray = ByteArray(Constants.FRAME_BUFFER_BYTES),
    val registers: ByteArray = ByteArray(Constants.REGISTER_BYTES),
    val stack: ByteArray = ByteArray(Constants.STACK_BYTES),
    var stackPointer: UByte = 0u,
    var delayTimer: UByte = 0u,
    var soundTimer: UByte = 0u,
    var indexRegister: UShort = 0u,
    var programCounter: UShort = 0x200u,
)