package app.nahiluhmot.kc8.emulator

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

class PeriodicExecutorTest {
    private val executor = PeriodicExecutor(10)

    @Test
    fun testRunFast() {
        val indexes = ArrayDeque<Int>()
        val times = ArrayDeque<Long>()

        runBlocking {
            val job = launch {
                executor.run { i ->
                    indexes.add(i)
                    times.add(System.currentTimeMillis())
                }
            }

            delay(150)

            job.cancel()
        }

        assertEquals(2, times.size)
        assertEquals(2, indexes.size)

        val diff = times[1] - times[0]

        assertTrue(diff in 100..110, "Expected a difference of 100ms, got: ${diff}ms")
        assertEquals(0, indexes[0])
        assertEquals(1, indexes[1])
    }
}