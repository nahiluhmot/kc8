package app.nahiluhmot.kc8

@OptIn(ExperimentalUnsignedTypes::class)
/**
 * Interface for display drivers.
 */
interface DisplayDriver {
    /**
     * Start the display driver. This method is always called first; the behavior of other methods
     * is undefined when this method has not been called.
     *
     * @param keyHandler handles keyboard input
     */
    fun startUp(keyHandler: KeyHandler)

    /**
     * Stop the display driver. The behavior of other methods is undefined after this method has been
     * called. Display drivers are not expected to be restartable.
     */
    fun shutDown()

    /**
     * Render a buffer to the screen.
     *
     * @param frameBuffer the buffer to render
     */
    fun render(frameBuffer: FrameBuffer)
}