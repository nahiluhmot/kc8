package app.nahiluhmot.kc8.io.clip

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import kotlin.math.sin

/**
 * Builds a clip.
 */
object ClipFactory {
    private const val FREQUENCY = 440.0 // A4
    private const val SAMPLE_RATE = 8000
    private const val VOLUME = 0.30 // 30%

    /**
     * Build the Clip.
     *
     * @return a Clip.
     */
    fun build(): Clip {
        val clip = AudioSystem.getClip()
        val buffer = ByteArray(SAMPLE_RATE / 10) // 100ms

        for (i in buffer.indices) {
            val angle =
                (2.0 * Math.PI * i * FREQUENCY) / SAMPLE_RATE

            buffer[i] = (sin(angle) * 127 * VOLUME).toInt().toByte()
        }

        val format = AudioFormat(SAMPLE_RATE.toFloat(), 8, 1, true, false)

        clip.open(format, buffer, 0, buffer.size)

        return clip
    }
}