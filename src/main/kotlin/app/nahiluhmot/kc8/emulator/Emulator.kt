package app.nahiluhmot.kc8.emulator

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.data.FrameBuffer
import app.nahiluhmot.kc8.data.State
import app.nahiluhmot.kc8.io.AudioDriver
import app.nahiluhmot.kc8.io.Loader
import app.nahiluhmot.kc8.io.StatefulKeyHandler
import app.nahiluhmot.kc8.io.UiDriver
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
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
    private var renderJob: Job? = null
    private val state = State()
    private val cpu = Cpu(state)
    private val renderChannel = Channel<FrameBuffer>(CHANNEL_CAPACITY)

    companion object {
        private const val CHANNEL_CAPACITY = 2
    }

    /**
     * Load a program into the emulator.
     */
    fun boot(program: File) {
        Loader.loadFont(state)
        Loader.loadProgram(program, state)
    }

    suspend fun run() = coroutineScope {
        uiDriver.startUp(StatefulKeyHandler(state))
        audioDriver.startUp()

        cpuJob = launch { runCpu() }
        renderJob = launch { runRender() }

        cpuJob?.join()
        renderJob?.join()
    }

    suspend fun shutDown() {
        cpuJob?.cancelAndJoin()
        renderJob?.cancelAndJoin()

        renderChannel.close()

        audioDriver.shutDown()
        uiDriver.shutDown()
    }

    private suspend fun runCpu() {
        var cpuCycle = 0
        var displayCycle = 0

        return PeriodicExecutor(Constants.REFRESH_RATE_HZ).run {
            val nextDisplayCycle = displayCycle + 1
            val nextCpuCycle = ((displayCycle.toDouble() / Constants.REFRESH_RATE_HZ) * cpuFrequencyHz).toInt()

            cpu.countDownTimers()

            repeat(nextCpuCycle - cpuCycle) {
                val opCode = cpu.decodeOpCode()

                cpu.executeOpCode(opCode)
            }

            if (state.renderFlag) {
                // Copy the frame buffer to prevent it from being overwritten mid-write.
                renderChannel.send(state.frameBuffer.copyOf())

                state.renderFlag = false
            }

            if (state.soundTimer > 0u) {
                audioDriver.startBeep()
            } else {
                audioDriver.stopBeep()
            }

            if (nextDisplayCycle == Constants.REFRESH_RATE_HZ) {
                cpuCycle = 0
                displayCycle = 0
            } else {
                cpuCycle = nextCpuCycle
                displayCycle = nextDisplayCycle
            }
        }
    }

    private suspend fun runRender() = coroutineScope {
        while (isActive) {
            val frame = renderChannel.receiveCatching().getOrNull() ?: continue

            uiDriver.render(frame)
        }
    }
}