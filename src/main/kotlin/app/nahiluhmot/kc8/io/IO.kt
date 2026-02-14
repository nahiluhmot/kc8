package app.nahiluhmot.kc8.io

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Interface for IO drivers.
 */
interface IO {
    /**
     * Start the IO driver. This method is always called first; the behavior of other methods when
     * this method has not been called.
     */
    fun startUp()

    /**
     * Stop the IO driver. The behavior of other methods is not defined after this method has been
     * called. IO drivers are not expected to be restartable.
     */
    fun shutDown()

    /**
     * Render a buffer to the screen.
     *
     * @param frameBuffer the buffer to render
     */
    fun render(frameBuffer: UByteArray)

    /**
     * Predicate to test whether a key is pressed. Chip-8 supports input from 0-9, A-F.
     *
     * @param key the key to test
     */
    fun isKeyPressed(key: UByte): Boolean

    /**
     * Start emitting a sound.
     */
    fun startSound()

    /**
     * Stop emitting a sound.
     */
    fun stopSound()
}