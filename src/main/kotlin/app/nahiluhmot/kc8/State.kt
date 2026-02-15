package app.nahiluhmot.kc8

@OptIn(ExperimentalUnsignedTypes::class)
class State(
    val memory: UByteArray = UByteArray(Constants.MEMORY_BYTES),
    val frameBuffer: UByteArray = UByteArray(Constants.FRAME_BUFFER_BYTES),
    val registers: UByteArray = UByteArray(Constants.REGISTER_BYTES),
    val stack: UByteArray = UByteArray(Constants.STACK_BYTES),
    var stackPointer: UByte = Constants.INITIAL_STACK_POINTER,
    var delayTimer: UByte = Constants.INITIAL_DELAY_TIMER,
    var soundTimer: UByte = Constants.INITIAL_SOUND_TIMER,
    var indexRegister: UShort = Constants.INITIAL_INDEX_REGISTER,
    var programCounter: UShort = Constants.INITIAL_PROGRAM_COUNTER,
)