package app.nahiluhmot.kc8.emulator

import app.nahiluhmot.kc8.data.FrameBufferMutations
import app.nahiluhmot.kc8.data.KeySetQueries
import app.nahiluhmot.kc8.data.OpCode
import app.nahiluhmot.kc8.data.State
import kotlin.random.Random
import kotlin.random.nextUInt

/**
 * Executes OpCodes.
 *
 * @param state the current CPU state
 * @param rng a random number generator
 */
@OptIn(ExperimentalUnsignedTypes::class)
class Cpu(val state: State, val rng: Random = Random.Default) {
    /**
     * Decodes the op code at the current program counter and executes it.
     */
    fun decodeAndExecute() {
        val opCode = decodeOpCode()

        executeOpCode(opCode)
    }

    /**
     * Decode the op code at the current program counter.
     *
     * @return the decoded op code
     * @throws IllegalStateException when the op code is invalid
     */
    fun decodeOpCode(): OpCode {
        val upper = state.memory[state.programCounter].toUInt()
        val lower = state.memory[state.programCounter + 1].toUInt()
        val raw = (upper shl 8) or lower

        return Decoder.decode(raw)
            ?: throw IllegalStateException("Invalid op code at 0x${state.programCounter.toHexString()}: 0x${raw.toHexString()}")
    }

    /**
     * Execute the given op code.
     *
     * @param opCode the op code to execute
     */
    fun executeOpCode(opCode: OpCode) {
        val incPc = when (opCode) {
            is OpCode.Add -> executeAdd(opCode)
            is OpCode.AddIndex -> executeAddIndex(opCode)
            is OpCode.Call -> executeCall(opCode)
            OpCode.ClearScreen -> executeClearScreen()
            is OpCode.Draw -> executeDraw(opCode)
            is OpCode.Jump -> executeJump(opCode)
            is OpCode.Load -> executeLoad(opCode)
            is OpCode.LoadDelay -> executeLoadDelay(opCode)
            is OpCode.LoadRegisters -> executeLoadRegisters(opCode)
            is OpCode.LoadSprite -> executeLoadSprite(opCode)
            is OpCode.Random -> executeRandom(opCode)
            is OpCode.Reg0Jump -> executeReg0Jump(opCode)
            is OpCode.RegAdd -> executeRegAdd(opCode)
            is OpCode.RegAnd -> executeRegAnd(opCode)
            is OpCode.RegLoad -> executeRegLoad(opCode)
            is OpCode.RegOr -> executeRegOr(opCode)
            is OpCode.RegSub -> executeRegSub(opCode)
            is OpCode.RegSubReverse -> executeRegSubReverse(opCode)
            is OpCode.RegXor -> executeRegXor(opCode)
            OpCode.Return -> executeReturn()
            is OpCode.SetDelay -> executeSetDelay(opCode)
            is OpCode.SetIndex -> executeSetIndex(opCode)
            is OpCode.SetSound -> executeSetSound(opCode)
            is OpCode.RegShiftL -> executeRegShiftL(opCode)
            is OpCode.RegShiftR -> executeRegShiftR(opCode)
            is OpCode.SkipEqual -> executeSkipEqual(opCode)
            is OpCode.SkipKey -> executeSkipKey(opCode)
            is OpCode.SkipNotEqual -> executeSkipNotEqual(opCode)
            is OpCode.SkipNotKey -> executeSkipNotKey(opCode)
            is OpCode.SkipRegEqual -> executeSkipRegEqual(opCode)
            is OpCode.SkipRegNotEqual -> executeSkipRegNotEqual(opCode)
            is OpCode.StoreBcd -> executeStoreBcd(opCode)
            is OpCode.StoreRegisters -> executeStoreRegisters(opCode)
            is OpCode.SysCall -> executeSysCall()
            is OpCode.WaitForKey -> executeWaitForKey(opCode)
        }

        if (incPc) {
            state.programCounter += 2
        }
    }

    private fun executeLoadRegisters(opCode: OpCode.LoadRegisters): Boolean {
        for (reg in 0..opCode.reg) {
            state.registers[reg] = state.memory[state.indexRegister + reg]
        }

        state.indexRegister += opCode.reg + 1

        return true
    }

    private fun executeStoreRegisters(opCode: OpCode.StoreRegisters): Boolean {
        for (reg in 0..opCode.reg) {
            state.memory[state.indexRegister + reg] = state.registers[reg]
        }

        state.indexRegister += opCode.reg + 1

        return true
    }

    private fun executeStoreBcd(opCode: OpCode.StoreBcd): Boolean {
        val byte = state.registers[opCode.reg]
        val hundreds = byte / 100u
        val tens = (byte / 10u) % 10u
        val ones = byte % 10u

        state.memory[state.indexRegister] = hundreds.toUByte()
        state.memory[state.indexRegister + 1] = tens.toUByte()
        state.memory[state.indexRegister + 2] = ones.toUByte()

        return true
    }

    private fun executeLoadSprite(opCode: OpCode.LoadSprite): Boolean {
        // The built-in sprites are 5 rows tall.
        state.indexRegister = state.registers[opCode.reg].toInt() * 5

        return true
    }

    private fun executeAddIndex(opCode: OpCode.AddIndex): Boolean {
        state.indexRegister += state.registers[opCode.reg].toInt()

        return true
    }

    private fun executeLoadDelay(opCode: OpCode.LoadDelay): Boolean {
        state.registers[opCode.reg] = state.delayTimer

        return true
    }

    private fun executeWaitForKey(opCode: OpCode.WaitForKey): Boolean {
        val key = KeySetQueries.getPressedKey(state.keySet)

        if (key == null) {
            return false
        } else {
            state.registers[opCode.reg] = key

            return true
        }
    }

    private fun executeSetDelay(opCode: OpCode.SetDelay): Boolean {
        state.delayTimer = state.registers[opCode.reg]

        return true
    }

    private fun executeSetSound(opCode: OpCode.SetSound): Boolean {
        state.soundTimer = state.registers[opCode.reg]

        return true
    }

    private fun executeSkipKey(opCode: OpCode.SkipKey): Boolean {
        if (KeySetQueries.isKeyPressed(state.keySet, state.registers[opCode.reg])) {
            state.programCounter += 4

            return false
        } else {
            return true
        }
    }

    private fun executeSkipNotKey(opCode: OpCode.SkipNotKey): Boolean {
        if (KeySetQueries.isKeyPressed(state.keySet, state.registers[opCode.reg])) {
            return true
        } else {
            state.programCounter += 4

            return false
        }
    }

    private fun executeDraw(opCode: OpCode.Draw): Boolean {
        var anyErased = false

        for (i in 0 until opCode.height) {
            val erased = FrameBufferMutations.drawUByte(
                state.frameBuffer,
                state.memory[state.indexRegister + i],
                opCode.x,
                opCode.y + i
            )

            anyErased = anyErased || erased
        }

        state.registers[0xF] = if (anyErased) 1u else 0u

        return true
    }

    private fun executeRandom(opCode: OpCode.Random): Boolean {
        state.registers[opCode.reg] = rng.nextUInt().toUByte() and opCode.byte

        return true
    }

    private fun executeSetIndex(opCode: OpCode.SetIndex): Boolean {
        state.indexRegister = opCode.addr

        return true
    }

    private fun executeRegShiftL(opCode: OpCode.RegShiftL): Boolean {
        val value = state.registers[opCode.reg]

        state.registers[0xF] = if ((value and 0x80u) > 0u) 1u else 0u
        state.registers[opCode.reg] = (value.toUInt() shl 1).toUByte()

        return true
    }

    private fun executeRegShiftR(opCode: OpCode.RegShiftR): Boolean {
        val value = state.registers[opCode.reg]

        state.registers[0xF] = if ((value and 0x1u) > 0u) 1u else 0u
        state.registers[opCode.reg] = (value.toUInt() shr 1).toUByte()

        return true
    }

    private fun executeRegSub(opCode: OpCode.RegSub): Boolean {
        val dst = state.registers[opCode.dst]
        val src = state.registers[opCode.src]

        state.registers[0xF] = if (dst > src) 1u else 0u
        state.registers[opCode.dst] = (dst - src).toUByte()


        return true
    }

    private fun executeRegSubReverse(opCode: OpCode.RegSubReverse): Boolean {
        val dst = state.registers[opCode.dst]
        val src = state.registers[opCode.src]

        state.registers[0xF] = if (src > dst) 1u else 0u
        state.registers[opCode.dst] = (src - dst).toUByte()

        return true
    }

    private fun executeRegAdd(opCode: OpCode.RegAdd): Boolean {
        val sum = state.registers[opCode.dst].toUInt() + state.registers[opCode.src].toUInt()

        state.registers[0xF] = if (sum > 0xFFu) 1u else 0u
        state.registers[opCode.dst] = sum.toUByte()

        return true
    }

    private fun executeRegXor(opCode: OpCode.RegXor): Boolean {
        state.registers[opCode.dst] = state.registers[opCode.dst] xor state.registers[opCode.src]

        return true
    }

    private fun executeRegAnd(opCode: OpCode.RegAnd): Boolean {
        state.registers[opCode.dst] = state.registers[opCode.dst] and state.registers[opCode.src]

        return true
    }

    private fun executeRegOr(opCode: OpCode.RegOr): Boolean {
        state.registers[opCode.dst] = state.registers[opCode.dst] or state.registers[opCode.src]

        return true
    }

    private fun executeRegLoad(opCode: OpCode.RegLoad): Boolean {
        state.registers[opCode.dst] = state.registers[opCode.src]

        return true
    }

    private fun executeAdd(opCode: OpCode.Add): Boolean {
        state.registers[opCode.reg] = (state.registers[opCode.reg] + opCode.byte).toUByte()

        return true
    }

    private fun executeLoad(opCode: OpCode.Load): Boolean {
        state.registers[opCode.reg] = opCode.byte

        return true
    }

    private fun executeSkipRegEqual(opCode: OpCode.SkipRegEqual): Boolean {
        if (state.registers[opCode.left] == state.registers[opCode.right]) {
            state.programCounter += 4

            return true
        } else {
            return false
        }
    }

    private fun executeSkipRegNotEqual(opCode: OpCode.SkipRegNotEqual): Boolean {
        if (state.registers[opCode.left] == state.registers[opCode.right]) {
            return false
        } else {
            state.programCounter += 4

            return true
        }
    }

    private fun executeSkipNotEqual(opCode: OpCode.SkipNotEqual): Boolean {
        if (state.registers[opCode.reg] == opCode.byte) {
            return true
        } else {
            state.programCounter += 4

            return false
        }
    }

    private fun executeSkipEqual(opCode: OpCode.SkipEqual): Boolean {
        if (state.registers[opCode.reg] == opCode.byte) {
            state.programCounter += 4

            return false
        } else {
            return true
        }
    }

    private fun executeCall(opCode: OpCode.Call): Boolean {
        state.stackPointer += 1
        state.stack[state.stackPointer] = state.programCounter
        state.programCounter = opCode.addr

        return false
    }

    private fun executeJump(opCode: OpCode.Jump): Boolean {
        state.programCounter = opCode.addr

        return false
    }

    private fun executeReg0Jump(opCode: OpCode.Reg0Jump): Boolean {
        state.programCounter = opCode.addr + state.registers[0].toInt()

        return false
    }

    private fun executeClearScreen(): Boolean {
        FrameBufferMutations.clear(state.frameBuffer)

        return true
    }

    private fun executeReturn(): Boolean {
        state.programCounter = state.stack[state.stackPointer]
        state.stackPointer -= 1

        return false
    }

    private fun executeSysCall(): Boolean {
        return true
    }
}