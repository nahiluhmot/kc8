package app.nahiluhmot.kc8

@OptIn(ExperimentalUnsignedTypes::class)
class State(
    val memory: UByteArray = UByteArray(Constants.MEMORY_BYTES),
    val frameBuffer: UByteArray = UByteArray(Constants.FRAME_BUFFER_BYTES),
    val registers: UByteArray = UByteArray(Constants.REGISTER_BYTES),
    val stack: UByteArray = UByteArray(Constants.STACK_BYTES),
    var stackPointer: UByte = 0u,
    var delayTimer: UByte = 0u,
    var soundTimer: UByte = 0u,
    var indexRegister: UShort = Constants.INITIAL_INDEX_REGISTER,
    var programCounter: UShort = Constants.INITIAL_PROGRAM_COUNTER,
)