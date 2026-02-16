package app.nahiluhmot.kc8.clip

import app.nahiluhmot.kc8.AudioDriver
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import kotlin.math.sin

class ClipAudioDriver : AudioDriver {
    private val clip = AudioSystem.getClip()

    companion object {
        private const val FREQUENCY = 440.0 // A4
        private const val SAMPLE_RATE = 8000
    }

    override fun startUp() {
        val buffer = ByteArray(SAMPLE_RATE / 10) // 100ms

        for (i in buffer.indices) {
            val angle = (2.0 * Math.PI * i * FREQUENCY) / SAMPLE_RATE

            buffer[i] = (sin(angle) * 127 * 0.30).toInt().toByte() // 30% volume
        }

        val format = AudioFormat(SAMPLE_RATE.toFloat(), 8, 1, true, false)

        clip.open(format, buffer, 0, buffer.size)
    }

    override fun shutDown() {
        if (clip.isRunning) clip.stop()

        clip.close()
    }

    override fun startBeep() {
        if (clip.isRunning) return

        clip.loop(Clip.LOOP_CONTINUOUSLY)
    }

    override fun stopBeep() {
        clip.stop()
    }
}