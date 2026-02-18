package app.nahiluhmot.kc8.emulator

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.data.OpCode
import app.nahiluhmot.kc8.data.State
import app.nahiluhmot.kc8.io.FontLoader
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalUnsignedTypes::class)
class CpuTest {
    private lateinit var cpu: Cpu
    private lateinit var state: State

    @MockK()
    private lateinit var rng: Random

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)

        state = State()

        FontLoader.loadFont(state)

        cpu = Cpu(state, rng)
    }

    @Test
    fun testDecodeOpCodeValid() {
        writeOpCode(0x00E0u)

        assertEquals(OpCode.ClearScreen, cpu.decodeOpCode())
    }

    @Test
    fun testDecodeOpCodeInvalid() {
        writeOpCode(0xEEEEu)

        assertFailsWith<IllegalStateException> { cpu.decodeOpCode() }
    }

    @Test
    fun testDecodeOpCodeOutOfBounds() {
        state.programCounter = 0xFFFF

        assertFailsWith<ArrayIndexOutOfBoundsException> { cpu.decodeOpCode() }
    }

    @Test
    fun testSysCall() {
        cpu.executeOpCode(OpCode.SysCall(0x000))

        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)
        assertZeros(skipProgramCounter = true)
    }

    @Test
    fun testClearScreen() {
        state.frameBuffer.fill(0x1u)

        cpu.executeOpCode(OpCode.ClearScreen)

        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)
        assertZeros(skipProgramCounter = true)
    }

    @Test
    fun testReturnValid() {
        state.stackPointer = 1
        state.stack[state.stackPointer] = 0x400

        cpu.executeOpCode(OpCode.Return)

        assertZeros(skipProgramCounter = true, skipStack = true)
        assertEquals(0x400, state.programCounter)

        for (i in state.stack.indices) {
            if (i == 1) {
                assertEquals(0x400, state.stack[i])
            } else {
                assertEquals(0x0, state.stack[i])
            }
        }
    }

    @Test
    fun testReturnInvalid() {
        state.stackPointer = -1

        assertFailsWith<ArrayIndexOutOfBoundsException> { cpu.executeOpCode(OpCode.Return) }
    }

    @Test
    fun testJump() {
        cpu.executeOpCode(OpCode.Jump(0x800))

        assertZeros(skipProgramCounter = true)
        assertEquals(0x800, state.programCounter)
    }

    @Test
    fun testCall() {
        cpu.executeOpCode(OpCode.Call(0xF00))

        assertZeros(skipProgramCounter = true, skipStack = true, skipStackPointer = true)
        assertEquals(0xF00, state.programCounter)
        assertEquals(1, state.stackPointer)

        for (i in state.stack.indices) {
            if (i == 1) {
                assertEquals(
                    Constants.INITIAL_PROGRAM_COUNTER + 2,
                    state.stack[i],
                    "Expected state.stack[$i] to be the return address"
                )
            } else {
                assertEquals(
                    0,
                    state.stack[i],
                    "Expected state.stack[$i] to be 0"
                )
            }
        }
    }

    @Test
    fun skipEqualTrue() {
        cpu.executeOpCode(OpCode.SkipEqual(0x0, 0x0u))

        assertZeros(skipProgramCounter = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 4, state.programCounter)
    }

    @Test
    fun skipEqualFalse() {
        cpu.executeOpCode(OpCode.SkipEqual(0x0, 0xFu))

        assertZeros(skipProgramCounter = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)
    }

    @Test
    fun skipNotEqualTrue() {
        cpu.executeOpCode(OpCode.SkipEqual(0x0, 0x1u))

        assertZeros(skipProgramCounter = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)
    }

    @Test
    fun skipNotEqualFalse() {
        cpu.executeOpCode(OpCode.SkipEqual(0x0, 0x0u))

        assertZeros(skipProgramCounter = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 4, state.programCounter)
    }

    @Test
    fun skipRegEqualTrue() {
        cpu.executeOpCode(OpCode.SkipRegEqual(0x0, 0x1))

        assertZeros(skipProgramCounter = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 4, state.programCounter)
    }

    @Test
    fun skipRegEqualFalse() {
        state.registers[0x7] = 0xFFu

        cpu.executeOpCode(OpCode.SkipRegEqual(0x6, 0x7))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            if (i == 0x7) {
                assertEquals(0xFFu, state.registers[i], "Expected state.registers[$i] to be unchanged")
            } else {
                assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testLoad() {
        cpu.executeOpCode(OpCode.Load(0x9, 0x13u))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            if (i == 0x9) {
                assertEquals(0x13u, state.registers[i], "Expected state.registers[$i] to be changed")
            } else {
                assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testAdd() {
        state.registers[0xA] = 0xEEu

        cpu.executeOpCode(OpCode.Add(0xA, 0x2u))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            if (i == 0xA) {
                assertEquals(0xF0u, state.registers[i], "Expected state.registers[$i] to be changed")
            } else {
                assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegLoad() {
        state.registers[0xB] = 0x2Bu

        cpu.executeOpCode(OpCode.RegLoad(0x4, 0xB))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            if ((i == 0x4) || (i == 0xB)) {
                assertEquals(0x2Bu, state.registers[i], "Expected state.registers[$i] to be changed")
            } else {
                assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegOr() {
        state.registers[0x3] = 0xA5u
        state.registers[0x5] = 0x52u

        cpu.executeOpCode(OpCode.RegOr(0x5, 0x3))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x5 -> assertEquals(0xF7u, state.registers[i], "Expected state.registers[$i] to be changed")
                0x3 -> assertEquals(0xA5u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegAnd() {
        state.registers[0x3] = 0xF0u
        state.registers[0x5] = 0xEFu

        cpu.executeOpCode(OpCode.RegAnd(0x5, 0x3))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x5 -> assertEquals(0xE0u, state.registers[i], "Expected state.registers[$i] to be changed")
                0x3 -> assertEquals(0xF0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegXor() {
        state.registers[0x3] = 0x24u
        state.registers[0x5] = 0x64u

        cpu.executeOpCode(OpCode.RegXor(0x5, 0x3))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x5 -> assertEquals(0x40u, state.registers[i], "Expected state.registers[$i] to be changed")
                0x3 -> assertEquals(0x24u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegAddNoOverflow() {
        state.registers[0x3] = 0xB2u
        state.registers[0x5] = 0x04u

        cpu.executeOpCode(OpCode.RegAdd(0x5, 0x3))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x5 -> assertEquals(0xB6u, state.registers[i], "Expected state.registers[$i] to be changed")
                0x3 -> assertEquals(0xB2u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun regAddOverflow() {
        state.registers[0x3] = 0x02u
        state.registers[0x5] = 0xFFu

        cpu.executeOpCode(OpCode.RegAdd(0x5, 0x3))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x5 -> assertEquals(0x01u, state.registers[i], "Expected state.registers[$i] to be changed")
                0x3 -> assertEquals(0x2u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                0xF -> assertEquals(0x1u, state.registers[i], "Expected state.registers[$i] to be changed")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegSubNoUnderflow() {
        state.registers[0x3] = 0xA2u
        state.registers[0x5] = 0xC5u

        cpu.executeOpCode(OpCode.RegSub(0x5, 0x3))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x5 -> assertEquals(0x23u, state.registers[i], "Expected state.registers[$i] to be changed")
                0x3 -> assertEquals(0xA2u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                0xF -> assertEquals(0x1u, state.registers[i], "Expected state.registers[$i] to be changed")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegSubUnderflow() {
        state.registers[0x3] = 0x07u
        state.registers[0x5] = 0x04u

        cpu.executeOpCode(OpCode.RegSub(0x5, 0x3))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x5 -> assertEquals(0xFDu, state.registers[i], "Expected state.registers[$i] to be changed")
                0x3 -> assertEquals(0x07u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegShiftREven() {
        state.registers[0x6] = 0xAEu

        cpu.executeOpCode(OpCode.RegShiftR(0x6))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x6 -> assertEquals(0x57u, state.registers[i], "Expected state.registers[$i] to be changed")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegShiftROdd() {
        state.registers[0x6] = 0xAFu

        cpu.executeOpCode(OpCode.RegShiftR(0x6))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x6 -> assertEquals(0x57u, state.registers[i], "Expected state.registers[$i] to be changed")
                0xF -> assertEquals(0x1u, state.registers[i], "Expected state.registers[$i] to be changed")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegSubReverseNoUnderflow() {
        state.registers[0x3] = 0x01u
        state.registers[0x5] = 0x04u

        cpu.executeOpCode(OpCode.RegSubReverse(0x3, 0x5))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x3 -> assertEquals(0x03u, state.registers[i], "Expected state.registers[$i] to be changed")
                0x5 -> assertEquals(0x04u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                0xF -> assertEquals(0x1u, state.registers[i], "Expected state.registers[$i] to be changed")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegSubReverseUnderflow() {
        state.registers[0x3] = 0xFFu
        state.registers[0x5] = 0x01u

        cpu.executeOpCode(OpCode.RegSubReverse(0x3, 0x5))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x3 -> assertEquals(0x02u, state.registers[i], "Expected state.registers[$i] to be changed")
                0x5 -> assertEquals(0x01u, state.registers[i], "Expected state.registers[$i] to be unchanged")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegShiftLNoOverflow() {
        state.registers[0x6] = 0x40u

        cpu.executeOpCode(OpCode.RegShiftL(0x6))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x6 -> assertEquals(0x80u, state.registers[i], "Expected state.registers[$i] to be changed")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRegShiftLOverflow() {
        state.registers[0x6] = 0x81u

        cpu.executeOpCode(OpCode.RegShiftL(0x6))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            when (i) {
                0x6 -> assertEquals(0x02u, state.registers[i], "Expected state.registers[$i] to be changed")
                0xF -> assertEquals(0x1u, state.registers[i], "Expected state.registers[$i] to be changed")
                else -> assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun skipRegNotEqualTrue() {
        state.registers[0x1] = 0xFFu

        cpu.executeOpCode(OpCode.SkipRegNotEqual(0x0, 0x1))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 4, state.programCounter)

        for (i in state.registers.indices) {
            if (i == 0x1) {
                assertEquals(0xFFu, state.registers[i], "Expected state.registers[$i] to be unchanged")
            } else {
                assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun skipRegNotEqualFalse() {
        cpu.executeOpCode(OpCode.SkipRegNotEqual(0x6, 0x7))

        assertZeros(skipProgramCounter = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)
    }

    @Test
    fun testSetIndex() {
        cpu.executeOpCode(OpCode.SetIndex(0xABC))

        assertZeros(skipProgramCounter = true, skipIndexRegister = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)
        assertEquals(0xABC, state.indexRegister)
    }

    @Test
    fun testReg0Jump() {
        state.registers[0x0] = 0x08u

        cpu.executeOpCode(OpCode.Reg0Jump(0x800))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(0x808, state.programCounter)

        for (i in state.registers.indices) {
            if (i == 0x0) {
                assertEquals(0x08u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            } else {
                assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRandLiberalMask() {
        every { rng.nextInt() } returns 0x45

        cpu.executeOpCode(OpCode.Random(0x4, 0xFFu))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            if (i == 0x4) {
                assertEquals(0x45u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            } else {
                assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    @Test
    fun testRandConservativeMask() {
        every { rng.nextInt() } returns 0x77

        cpu.executeOpCode(OpCode.Random(0x4, 0x01u))

        assertZeros(skipProgramCounter = true, skipRegisters = true)
        assertEquals(Constants.INITIAL_PROGRAM_COUNTER + 2, state.programCounter)

        for (i in state.registers.indices) {
            if (i == 0x4) {
                assertEquals(0x01u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            } else {
                assertEquals(0x0u, state.registers[i], "Expected state.registers[$i] to be unchanged")
            }
        }
    }

    private fun writeOpCode(raw: UShort) {
        state.memory[Constants.INITIAL_PROGRAM_COUNTER] = (raw.toUInt() shr 8).toUByte()
        state.memory[Constants.INITIAL_PROGRAM_COUNTER + 1] = raw.toUByte()
    }

    private fun assertZeros(
        skipMemory: Boolean = false,
        skipFrameBuffer: Boolean = false,
        skipRegisters: Boolean = false,
        skipStack: Boolean = false,
        skipStackPointer: Boolean = false,
        skipDelayTimer: Boolean = false,
        skipSoundTimer: Boolean = false,
        skipIndexRegister: Boolean = false,
        skipProgramCounter: Boolean = false,
        skipKeySet: Boolean = false,
    ) {
        if (!skipMemory) {
            // Fonts are stored in 0..79
            for (i in 80 until state.memory.size) {
                assertEquals(0u, state.memory[i], "Expected memory[$i] to be unchanged")
            }
        }

        if (!skipFrameBuffer) {
            for (i in 0 until state.frameBuffer.size) {
                assertEquals(0u, state.frameBuffer[i], "Expected frameBuffer[$i] to be unchanged")
            }
        }

        if (!skipRegisters) {
            for (i in 0 until state.registers.size) {
                assertEquals(0u, state.registers[i], "Expected registers[$i] to be unchanged")
            }
        }

        if (!skipStack) {
            for (i in 0 until state.stack.size) {
                assertEquals(0, state.stack[i], "Expected stack[$i] to be unchanged")
            }
        }

        if (!skipStackPointer) {
            assertEquals(0, state.stackPointer, "Expected stackPointer to be unchanged")
        }

        if (!skipDelayTimer) {
            assertEquals(0u, state.delayTimer, "Expected delayTimer to be unchanged")
        }

        if (!skipSoundTimer) {
            assertEquals(0u, state.soundTimer, "Expected soundTimer to be unchanged")
        }

        if (!skipIndexRegister) {
            assertEquals(0, state.indexRegister, "Expected indexRegister to be unchanged")
        }

        if (!skipProgramCounter) {
            assertEquals(0, state.programCounter, "Expected programCounter to be unchanged")
        }

        if (!skipKeySet) {
            assertEquals(0u, state.keySet, "Expected keySet to be unchanged")
        }
    }
}