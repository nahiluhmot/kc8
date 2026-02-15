package app.nahiluhmot.kc8

private const val ZERO: UShort = 0u

object KeyQueries {
    fun addKey(pressedKeys: PressedKeys, key: UByte) =
        pressedKeys or (1 shl key.toInt()).toUShort()

    fun removeKey(pressedKeys: PressedKeys, key: UByte) =
        pressedKeys and (1 shl key.toInt()).inv().toUShort()

    fun isKeyPressed(pressedKeys: PressedKeys, key: UByte): Boolean {
        return (pressedKeys and (1 shl key.toInt()).toUShort()) != ZERO
    }

    fun getPressedKey(pressedKeys: PressedKeys) =
        if (pressedKeys == ZERO) {
            null
        } else {
            pressedKeys.countTrailingZeroBits().toUByte()
        }
}