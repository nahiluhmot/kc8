package app.nahiluhmot.kc8

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Interface for IO drivers.
 */
interface IO {
    /**
     * Start the IO driver. This method is always called first; the behavior of other methods is
     * undefined when this method has not been called.
     *
     * @param keyHandler handles keyboard input
     */
    fun startUp(keyHandler: KeyHandler)

    /**
     * Stop the IO driver. The behavior of other methods is undefined after this method has been
     * called. IO drivers are not expected to be restartable.
     */
    fun shutDown()

    /**
     * Render a buffer to the screen.
     *
     * @param frameBuffer the buffer to render
     */
    fun render(frameBuffer: FrameBuffer)
}