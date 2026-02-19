package app.nahiluhmot.kc8.emulator

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.yield

/**
 * Executes a function at the specified frequency.
 *
 * @param frequencyHz the frequency at which the function should be executed
 */
class PeriodicExecutor(val frequencyHz: Int) {
    val timePerExecutionNs: Long = 1_000_000_000L / frequencyHz
    val catchupMax = frequencyHz * MAX_SECONDS_BEHIND

    companion object {
        private const val SAFE_DELAY_DURATION_NS = 5_000_000L // 5ms
        private const val DELAY_VALUE_MS = 1L
        private const val MAX_SECONDS_BEHIND = 2
    }

    suspend fun run(func: () -> Unit) = coroutineScope {
        var nextExecutionNs = System.nanoTime()

        while (isActive) {
            val now = System.nanoTime()

            if (now >= nextExecutionNs) {
                func.invoke()

                nextExecutionNs =
                    if (((now - nextExecutionNs) / timePerExecutionNs) >= catchupMax) {
                        now + timePerExecutionNs
                    } else {
                        nextExecutionNs + timePerExecutionNs
                    }
            } else if ((nextExecutionNs - now) < SAFE_DELAY_DURATION_NS) {
                yield()
            } else {
                delay(DELAY_VALUE_MS)
            }
        }
    }
}