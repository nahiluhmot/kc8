@file:OptIn(ExperimentalUnsignedTypes::class)

package app.nahiluhmot.kc8

import app.nahiluhmot.kc8.emulator.Emulator
import app.nahiluhmot.kc8.io.clip.ClipAudioDriver
import app.nahiluhmot.kc8.io.swing.SwingDisplayDriver
import kotlinx.coroutines.runBlocking
import java.io.File

/**
 * Application entrypoint.
 */
fun main(args: Array<String>) {
    val uiDriver = SwingDisplayDriver()
    val audioDriver = ClipAudioDriver()
    val emulator = Emulator(args[0].toInt(), audioDriver, uiDriver)
    val program = File(args[1])

    emulator.boot(program)

    runBlocking {
        try {
            emulator.run()
        } finally {
            emulator.shutDown()
        }
    }
}
