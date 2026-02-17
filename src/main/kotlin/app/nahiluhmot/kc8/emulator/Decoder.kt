package app.nahiluhmot.kc8.emulator

import app.nahiluhmot.kc8.data.OpCode

/**
 * Decodes op codes.
 */
object Decoder {
    /**
     * Decode an op code. This interface accepts UInts when a UShort would do, because you cannot
     * shift UShorts in kotlin without converting.
     *
     * @param raw the raw op code
     * @return the parsed op code, or null if the op code is invalid
     * @throw IllegalStateException when an internal error occurs
     */
    fun decode(raw: UInt): OpCode? =
        when (getNthNibble(raw, 3)) {
            0x0u -> decode0(raw)
            0x1u -> decode1(raw)
            0x2u -> decode2(raw)
            0x3u -> decode3(raw)
            0x4u -> decode4(raw)
            0x5u -> decode5(raw)
            0x6u -> decode6(raw)
            0x7u -> decode7(raw)
            0x8u -> decode8(raw)
            0x9u -> decode9(raw)
            0xAu -> decodeA(raw)
            0xBu -> decodeB(raw)
            0xCu -> decodeC(raw)
            0xDu -> decodeD(raw)
            0xEu -> decodeE(raw)
            0xFu -> decodeF(raw)
            else -> throw IllegalStateException("Internal Decoder error: nibble not in range 0x0-0xF")
        }

    private fun decode0(raw: UInt) =
        when (getLowerByte(raw)) {
            0xE0u -> OpCode.ClearScreen
            0xEEu -> OpCode.Return
            else -> OpCode.SysCall(getLowerAddress(raw).toInt())
        }

    private fun decode1(raw: UInt) =
        OpCode.Jump(getLowerAddress(raw).toInt())

    private fun decode2(raw: UInt) =
        OpCode.Call(getLowerAddress(raw).toInt())

    private fun decode3(raw: UInt) =
        OpCode.SkipEqual(
            getNthNibble(raw, 2).toInt(),
            getLowerByte(raw).toUByte()
        )

    private fun decode4(raw: UInt) =
        OpCode.SkipNotEqual(
            getNthNibble(raw, 2).toInt(),
            getLowerByte(raw).toUByte()
        )

    private fun decode5(raw: UInt) =
        if (getNthNibble(raw, 0) == 0u) {
            OpCode.SkipRegEqual(
                getNthNibble(raw, 2).toInt(),
                getNthNibble(raw, 1).toInt(),
            )
        } else {
            null
        }

    private fun decode6(raw: UInt) =
        OpCode.Load(
            getNthNibble(raw, 2).toInt(),
            getLowerByte(raw).toUByte()
        )

    private fun decode7(raw: UInt) =
        OpCode.Add(
            getNthNibble(raw, 2).toInt(),
            getLowerByte(raw).toUByte()
        )

    private fun decode8(raw: UInt) =
        when (getNthNibble(raw, 0)) {
            0x0u -> OpCode.RegLoad(getNthNibble(raw, 2).toInt(), getNthNibble(raw, 1).toInt())
            0x1u -> OpCode.RegOr(getNthNibble(raw, 2).toInt(), getNthNibble(raw, 1).toInt())
            0x2u -> OpCode.RegAnd(getNthNibble(raw, 2).toInt(), getNthNibble(raw, 1).toInt())
            0x3u -> OpCode.RegXor(getNthNibble(raw, 2).toInt(), getNthNibble(raw, 1).toInt())
            0x4u -> OpCode.RegAdd(getNthNibble(raw, 2).toInt(), getNthNibble(raw, 1).toInt())
            0x5u -> OpCode.RegSub(getNthNibble(raw, 2).toInt(), getNthNibble(raw, 1).toInt())
            0x6u -> OpCode.RegShiftR(getNthNibble(raw, 2).toInt())
            0x7u -> OpCode.RegSubReverse(getNthNibble(raw, 2).toInt(), getNthNibble(raw, 1).toInt())
            0xEu -> OpCode.RegShiftL(getNthNibble(raw, 2).toInt())
            else -> null
        }

    private fun decode9(raw: UInt) =
        if (getNthNibble(raw, 0) == 0u) {
            OpCode.SkipRegNotEqual(
                getNthNibble(raw, 2).toInt(),
                getNthNibble(raw, 1).toInt(),
            )
        } else {
            null
        }

    private fun decodeA(raw: UInt) =
        OpCode.SetIndex(getLowerAddress(raw).toInt())

    private fun decodeB(raw: UInt) =
        OpCode.Reg0Jump(getLowerAddress(raw).toInt())

    private fun decodeC(raw: UInt) =
        OpCode.Random(getNthNibble(raw, 2).toInt(), getLowerByte(raw).toUByte())

    private fun decodeD(raw: UInt) =
        OpCode.Draw(
            getNthNibble(raw, 2).toInt(),
            getNthNibble(raw, 1).toInt(),
            getNthNibble(raw, 0).toInt(),
        )

    private fun decodeE(raw: UInt) =
        when (getLowerByte(raw)) {
            0x9Eu -> OpCode.SkipKey(getNthNibble(raw, 2).toInt())
            0xA1u -> OpCode.SkipNotKey(getNthNibble(raw, 2).toInt())
            else -> null
        }

    private fun decodeF(raw: UInt) =
        when (getLowerByte(raw)) {
            0x07u -> OpCode.LoadDelay(getNthNibble(raw, 2).toInt())
            0x0Au -> OpCode.WaitForKey(getNthNibble(raw, 2).toInt())
            0x15u -> OpCode.SetDelay(getNthNibble(raw, 2).toInt())
            0x18u -> OpCode.SetSound(getNthNibble(raw, 2).toInt())
            0x1Eu -> OpCode.AddIndex(getNthNibble(raw, 2).toInt())
            0x29u -> OpCode.LoadSprite(getNthNibble(raw, 2).toInt())
            0x33u -> OpCode.StoreBcd(getNthNibble(raw, 2).toInt())
            0x55u -> OpCode.StoreRegisters(getNthNibble(raw, 2).toInt())
            0x65u -> OpCode.LoadRegisters(getNthNibble(raw, 2).toInt())
            else -> null
        }

    private fun getNthNibble(raw: UInt, n: Int) =
        (raw shr (n * 4)) and 0xFu

    private fun getLowerAddress(raw: UInt) =
        raw and 0xFFFu

    private fun getLowerByte(raw: UInt) =
        raw and 0xFFu
}