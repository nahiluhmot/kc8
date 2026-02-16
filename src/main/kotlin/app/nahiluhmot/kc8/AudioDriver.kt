package app.nahiluhmot.kc8

/**
 * Interface for audio drivers.
 */
interface AudioDriver {
    /**
     * Start the audio driver. This method must be called before other methods. The behavior of
     * other methods is not defined if this method has not been called.
     */
    fun startUp()

    /**
     * Stop the audio driver. The behavior of other methods is undefined after this method has been
     * called. audio drivers are not expected to be restartable.
     */
    fun shutDown()

    /**
     * Start emitting a beep. If a beep has already been started, this should be a no-op.
     */
    fun startBeep()

    /**
     * Stop emitting a beep. If a beep has not yet been started, this should be a no-op.
     */
    fun stopBeep()
}