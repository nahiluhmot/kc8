package app.nahiluhmot.kc8.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DecoderTest {
    @Test
    fun testDecodeSysCall() {
        assertEquals(
            OpCode.SysCall(0xF0A),
            Decoder.decode(0x0F0Au)
        )
    }

    @Test
    fun testDecodeClearScreen() {
        assertEquals(
            OpCode.ClearScreen,
            Decoder.decode(0x00E0u)
        )
    }

    @Test
    fun testDecodeReturn() {
        assertEquals(
            OpCode.Return,
            Decoder.decode(0x00EEu)
        )
    }

    @Test
    fun testDecodeJump() {
        assertEquals(
            OpCode.Jump(0x738),
            Decoder.decode(0x1738u)
        )
    }

    @Test
    fun testDecodeCall() {
        assertEquals(
            OpCode.Call(0x400),
            Decoder.decode(0x2400u)
        )
    }

    @Test
    fun testDecodeSkipEqual() {
        assertEquals(
            OpCode.SkipEqual(0x6, 0x79u),
            Decoder.decode(0x3679u)
        )
    }

    @Test
    fun testDecodeSkipNotEqual() {
        assertEquals(
            OpCode.SkipNotEqual(0x5, 0x67u),
            Decoder.decode(0x4567u)
        )
    }

    @Test
    fun testDecodeSkipRegEqual() {
        assertEquals(
            OpCode.SkipRegEqual(0x4, 0x9),
            Decoder.decode(0x5490u)
        )
    }

    @Test
    fun testDecodeLoad() {
        assertEquals(
            OpCode.Load(0xD, 0xEFu),
            Decoder.decode(0x6DEFu)
        )
    }

    @Test
    fun testDecodeAdd() {
        assertEquals(
            OpCode.Add(0xD, 0xEFu),
            Decoder.decode(0x7DEFu)
        )
    }

    @Test
    fun testDecodeRegLoad() {
        assertEquals(
            OpCode.RegLoad(0xD, 0xE),
            Decoder.decode(0x8DE0u)
        )
    }

    @Test
    fun testDecodeRegOr() {
        assertEquals(
            OpCode.RegOr(0xD, 0xE),
            Decoder.decode(0x8DE1u)
        )
    }

    @Test
    fun testDecodeRegAnd() {
        assertEquals(
            OpCode.RegAnd(0xD, 0xE),
            Decoder.decode(0x8DE2u)
        )
    }

    @Test
    fun testDecodeRegXor() {
        assertEquals(
            OpCode.RegXor(0xD, 0xE),
            Decoder.decode(0x8DE3u)
        )
    }

    @Test
    fun testDecodeRegAdd() {
        assertEquals(
            OpCode.RegAdd(0xD, 0xE),
            Decoder.decode(0x8DE4u)
        )
    }

    @Test
    fun testDecodeRegSub() {
        assertEquals(
            OpCode.RegSub(0xD, 0xE),
            Decoder.decode(0x8DE5u)
        )
    }

    @Test
    fun testDecodeShiftRight() {
        assertEquals(
            OpCode.ShiftRight(0xD),
            Decoder.decode(0x8DE6u)
        )
    }

    @Test
    fun testDecodeRegSubReverse() {
        assertEquals(
            OpCode.RegSubReverse(0xC, 0xA),
            Decoder.decode(0x8CA7u)
        )
    }

    @Test
    fun testDecodeShiftLeft() {
        assertEquals(
            OpCode.ShiftLeft(0xC),
            Decoder.decode(0x8CFEu)
        )
    }

    @Test
    fun testDecodeSkipRegNotEqual() {
        assertEquals(
            OpCode.SkipRegNotEqual(0xB, 0xE),
            Decoder.decode(0x9BE0u)
        )
    }

    @Test
    fun testDecodeSetIndex() {
        assertEquals(
            OpCode.SetIndex(0xBCD),
            Decoder.decode(0xABCDu)
        )
    }

    @Test
    fun testDecodeRegJump() {
        assertEquals(
            OpCode.Reg0Jump(0xDEF),
            Decoder.decode(0xBDEFu)
        )
    }

    @Test
    fun testDecodeRandom() {
        assertEquals(
            OpCode.Random(0x0, 0xFFu),
            Decoder.decode(0xC0FFu)
        )
    }

    @Test
    fun testDecodeDraw() {
        assertEquals(
            OpCode.Draw(0x9, 0xB, 0xE),
            Decoder.decode(0xD9BEu)
        )
    }

    @Test
    fun testDecodeSkipKey() {
        assertEquals(
            OpCode.SkipKey(0xB),
            Decoder.decode(0xEB9Eu)
        )
    }

    @Test
    fun testDecodeSkipNotKey() {
        assertEquals(
            OpCode.SkipNotKey(0x7),
            Decoder.decode(0xE7A1u)
        )
    }

    @Test
    fun testDecodeSetToDelay() {
        assertEquals(
            OpCode.SetToDelay(0x3),
            Decoder.decode(0xF307u)
        )
    }

    @Test
    fun testDecodeWaitForKey() {
        assertEquals(
            OpCode.WaitForKey(0x1),
            Decoder.decode(0xF10Au)
        )
    }

    @Test
    fun testDecodeSetDelay() {
        assertEquals(
            OpCode.SetDelay(0x2),
            Decoder.decode(0xF215u)
        )
    }

    @Test
    fun testDecodeSetSound() {
        assertEquals(
            OpCode.SetSound(0x2),
            Decoder.decode(0xF218u)
        )
    }

    @Test
    fun testDecodeAddIndex() {
        assertEquals(
            OpCode.AddIndex(0x2),
            Decoder.decode(0xF21Eu)
        )
    }

    @Test
    fun testDecodeLoadSprite() {
        assertEquals(
            OpCode.LoadSprite(0x8),
            Decoder.decode(0xF829u)
        )
    }

    @Test
    fun testDecodeStoreBcd() {
        assertEquals(
            OpCode.StoreBcd(0x8),
            Decoder.decode(0xF833u)
        )
    }

    @Test
    fun testDecodeStoreRegisters() {
        assertEquals(
            OpCode.StoreRegisters(0x8),
            Decoder.decode(0xF855u)
        )
    }

    @Test
    fun testDecodeLoadRegisters() {
        assertEquals(
            OpCode.LoadRegisters(0x8),
            Decoder.decode(0xF865u)
        )
    }

    @Test
    fun testDecodeInvalid() {
        val invalidOpCodes = listOf(0x5001u, 0x8888u, 0x9EE2u, 0xEAE9u, 0xFF00u)

        for (raw in invalidOpCodes) {
            assertNull(
                Decoder.decode(raw),
                "Expected 0x${raw.toHexString()} to be an invalid op code"
            )
        }
    }
}