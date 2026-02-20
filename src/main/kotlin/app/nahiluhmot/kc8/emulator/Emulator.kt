package app.nahiluhmot.kc8.emulator

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.data.State
import app.nahiluhmot.kc8.io.AudioDriver
import app.nahiluhmot.kc8.io.Loader
import app.nahiluhmot.kc8.io.StatefulKeyHandler
import app.nahiluhmot.kc8.io.UiDriver
import kotlinx.coroutines.*
import java.io.File

/**
 * Emulates a Chip-8 CPU.
 *
 * @param cpuFrequencyHz the frequency the CPU should run at
 * @param audioDriver system audio
 * @param uiDriver display & inputs
 */
@OptIn(ExperimentalUnsignedTypes::class, ExperimentalCoroutinesApi::class)
class Emulator(
    val cpuFrequencyHz: Int,
    val audioDriver: AudioDriver,
    val uiDriver: UiDriver
) {
    private var cpuJob: Job? = null
    private val state = State()
    private val cpu = Cpu(state)

    /**
     * Load a program into the emulator.
     */
    fun boot(program: File) {
        Loader.loadFont(state)
        Loader.loadProgram(program, state)

        uiDriver.startUp(StatefulKeyHandler(state))
        audioDriver.startUp()
    }

    suspend fun run() = coroutineScope {
        cpuJob = launch { runCpu() }

        cpuJob?.join()
    }

    suspend fun shutDown() {
        cpuJob?.cancelAndJoin()

        audioDriver.shutDown()
        uiDriver.shutDown()
    }

    private suspend fun runCpu() =
        PeriodicExecutor(Constants.REFRESH_RATE_HZ)
            .run(this::runDisplayCycle)

    private fun runDisplayCycle(displayCycle: Int) {
        val cpuCycle = (cpuFrequencyHz * displayCycle) / Constants.REFRESH_RATE_HZ
        val nextCpuCycle = (cpuFrequencyHz * (displayCycle + 1)) / Constants.REFRESH_RATE_HZ

        cpu.countDownTimers()

        repeat(nextCpuCycle - cpuCycle) {
            val opCode = cpu.decodeOpCode()

            cpu.executeOpCode(opCode)
        }

        if (state.renderFlag) {
            // Copy the frame buffer to prevent it from being overwritten mid-render.
            uiDriver.render(state.frameBuffer.copyOf())

            state.renderFlag = false
        }

        if (state.soundTimer > 0u) {
            audioDriver.startBeep()
        } else {
            audioDriver.stopBeep()
        }
    }
}