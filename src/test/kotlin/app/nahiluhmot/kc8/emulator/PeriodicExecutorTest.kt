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
        val ary = ArrayDeque<Long>()

        runBlocking {
            val job = launch {
                executor.run {
                    ary.add(System.currentTimeMillis())
                }
            }

            delay(150)

            job.cancel()
        }

        assertEquals(2, ary.size)

        val diff = ary[1] - ary[0]

        assertTrue(diff in 100..110, "Expected a difference of 500-600ms, got: ${diff}ms")
    }
}