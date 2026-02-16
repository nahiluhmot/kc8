package app.nahiluhmot.kc8.clip

import app.nahiluhmot.kc8.AudioDriver
import javax.sound.sampled.Clip

class ClipAudioDriver(val clip: Clip = ClipFactory.build()) : AudioDriver {
    override fun startUp() {
        // no-op
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