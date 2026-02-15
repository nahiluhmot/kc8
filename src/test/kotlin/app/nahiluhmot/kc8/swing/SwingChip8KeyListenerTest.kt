package app.nahiluhmot.kc8.swing

import app.nahiluhmot.kc8.KeyHandler
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import java.awt.event.KeyEvent
import javax.swing.JPanel
import kotlin.test.Test

class SwingChip8KeyListenerTest {
    @MockK()
    lateinit var keyHandler: KeyHandler

    private val component = JPanel()

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testKeyTyped() {
        val listener = SwingChip8KeyListener(keyHandler)

        listener.keyTyped(buildEvent(KeyEvent.KEY_TYPED, KeyEvent.VK_UNDEFINED, 'y'))

        confirmVerified(keyHandler)
    }

    @Test
    fun testRelevantKeyPressed() {
        val listener = SwingChip8KeyListener(keyHandler)

        every { keyHandler.onKeyDown(any()) } just Runs

        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_1))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_A))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_B))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_4))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_5))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_7))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_D))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_2))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_9))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_F))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_0))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_C))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_3))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_E))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_6))
        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_8))

        verifyOrder {
            keyHandler.onKeyDown(0x1u)
            keyHandler.onKeyDown(0xAu)
            keyHandler.onKeyDown(0xBu)
            keyHandler.onKeyDown(0x4u)
            keyHandler.onKeyDown(0x5u)
            keyHandler.onKeyDown(0x7u)
            keyHandler.onKeyDown(0xDu)
            keyHandler.onKeyDown(0x2u)
            keyHandler.onKeyDown(0x9u)
            keyHandler.onKeyDown(0xFu)
            keyHandler.onKeyDown(0x0u)
            keyHandler.onKeyDown(0xCu)
            keyHandler.onKeyDown(0x3u)
            keyHandler.onKeyDown(0xEu)
            keyHandler.onKeyDown(0x6u)
            keyHandler.onKeyDown(0x8u)
        }

        confirmVerified(keyHandler)
    }

    @Test
    fun testIrrelevantKeyPressed() {
        val listener = SwingChip8KeyListener(keyHandler)

        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_X))

        confirmVerified(keyHandler)
    }

    @Test
    fun testRelevantKeyReleased() {
        val listener = SwingChip8KeyListener(keyHandler)

        every { keyHandler.onKeyUp(any()) } just Runs

        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_9))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_F))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_0))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_C))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_3))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_E))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_6))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_8))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_1))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_A))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_B))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_4))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_5))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_7))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_D))
        listener.keyReleased(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_2))

        verifyOrder {
            keyHandler.onKeyUp(0x9u)
            keyHandler.onKeyUp(0xFu)
            keyHandler.onKeyUp(0x0u)
            keyHandler.onKeyUp(0xCu)
            keyHandler.onKeyUp(0x3u)
            keyHandler.onKeyUp(0xEu)
            keyHandler.onKeyUp(0x6u)
            keyHandler.onKeyUp(0x8u)
            keyHandler.onKeyUp(0x1u)
            keyHandler.onKeyUp(0xAu)
            keyHandler.onKeyUp(0xBu)
            keyHandler.onKeyUp(0x4u)
            keyHandler.onKeyUp(0x5u)
            keyHandler.onKeyUp(0x7u)
            keyHandler.onKeyUp(0xDu)
            keyHandler.onKeyUp(0x2u)
        }

        confirmVerified(keyHandler)
    }

    @Test

    fun testIrrelevantKeyRelesaed() {
        val listener = SwingChip8KeyListener(keyHandler)

        listener.keyPressed(buildEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_Z))

        confirmVerified(keyHandler)
    }

    private fun buildEvent(keyEvent: Int, keyCode: Int, keyChar: Char = KeyEvent.CHAR_UNDEFINED) =
        KeyEvent(
            component,
            keyEvent,
            System.currentTimeMillis(),
            0,
            keyCode,
            keyChar
        )
}