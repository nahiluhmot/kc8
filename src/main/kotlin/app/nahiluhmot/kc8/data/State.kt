package app.nahiluhmot.kc8.data

import app.nahiluhmot.kc8.Constants

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Maintains state of the interpreter.
 */
class State(
    val memory: Memory = UByteArray(Constants.MEMORY_BYTES),
    val frameBuffer: FrameBuffer = ULongArray(Constants.SCREEN_HEIGHT),
    val registers: Registers = UByteArray(Constants.REGISTER_BYTES),
    val stack: Stack = IntArray(Constants.STACK_BYTES),
    var stackPointer: StackPointer = Constants.INITIAL_STACK_POINTER,
    var delayTimer: DelayTimer = Constants.INITIAL_DELAY_TIMER,
    var soundTimer: SoundTimer = Constants.INITIAL_SOUND_TIMER,
    var indexRegister: IndexRegister = Constants.INITIAL_INDEX_REGISTER,
    var programCounter: ProgramCounter = Constants.INITIAL_PROGRAM_COUNTER,
    // Volatile because this value is written by the UI thread and read by the CPU thread.
    @Volatile
    var keySet: KeySet = Constants.DEFAULT_PRESSED_KEYS
)