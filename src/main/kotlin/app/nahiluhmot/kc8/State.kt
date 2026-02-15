package app.nahiluhmot.kc8

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Maintains state of the interpreter.
 */
class State(
    val memory: Memory = UByteArray(Constants.MEMORY_BYTES),
    val frameBuffer: FrameBuffer = UByteArray(Constants.FRAME_BUFFER_BYTES),
    val registers: Registers = UByteArray(Constants.REGISTER_BYTES),
    val stack: Stack = UByteArray(Constants.STACK_BYTES),
    var stackPointer: StackPointer = Constants.INITIAL_STACK_POINTER,
    var delayTimer: DelayTimer = Constants.INITIAL_DELAY_TIMER,
    var soundTimer: SoundTimer = Constants.INITIAL_SOUND_TIMER,
    var indexRegister: IndexRegister = Constants.INITIAL_INDEX_REGISTER,
    var programCounter: ProgramCounter = Constants.INITIAL_PROGRAM_COUNTER,
    var pressedKeys: PressedKeys = Constants.DEFAULT_PRESSED_KEYS
)