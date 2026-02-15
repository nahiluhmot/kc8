package app.nahiluhmot.kc8


object KeyQueries {
    private const val ZERO: UShort = 0u

    fun addKey(keySet: KeySet, key: UByte) =
        keySet or (1 shl key.toInt()).toUShort()

    fun removeKey(keySet: KeySet, key: UByte) =
        keySet and (1 shl key.toInt()).inv().toUShort()

    fun isKeyPressed(keySet: KeySet, key: UByte): Boolean {
        return (keySet and (1 shl key.toInt()).toUShort()) != ZERO
    }

    fun getPressedKey(keySet: KeySet) =
        if (keySet == ZERO) {
            null
        } else {
            keySet.countTrailingZeroBits().toUByte()
        }
}