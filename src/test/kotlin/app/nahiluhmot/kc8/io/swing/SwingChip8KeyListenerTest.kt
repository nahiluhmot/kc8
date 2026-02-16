package app.nahiluhmot.kc8.io.swing

import app.nahiluhmot.kc8.Constants
import app.nahiluhmot.kc8.io.KeyHandler
import io.mockk.*
import io.mockk.impl.annotations.MockK
import java.awt.event.KeyEvent
import javax.swing.JPanel
import kotlin.test.BeforeTest
import kotlin.test.Test

class SwingChip8KeyListenerTest {
    @MockK()
    lateinit var keyHandler: KeyHandler

    private val component = JPanel()

    @BeforeTest
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
            keyHandler.onKeyDown(Constants.KEY_1)
            keyHandler.onKeyDown(Constants.KEY_A)
            keyHandler.onKeyDown(Constants.KEY_B)
            keyHandler.onKeyDown(Constants.KEY_4)
            keyHandler.onKeyDown(Constants.KEY_5)
            keyHandler.onKeyDown(Constants.KEY_7)
            keyHandler.onKeyDown(Constants.KEY_D)
            keyHandler.onKeyDown(Constants.KEY_2)
            keyHandler.onKeyDown(Constants.KEY_9)
            keyHandler.onKeyDown(Constants.KEY_F)
            keyHandler.onKeyDown(Constants.KEY_0)
            keyHandler.onKeyDown(Constants.KEY_C)
            keyHandler.onKeyDown(Constants.KEY_3)
            keyHandler.onKeyDown(Constants.KEY_E)
            keyHandler.onKeyDown(Constants.KEY_6)
            keyHandler.onKeyDown(Constants.KEY_8)
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
            keyHandler.onKeyUp(Constants.KEY_9)
            keyHandler.onKeyUp(Constants.KEY_F)
            keyHandler.onKeyUp(Constants.KEY_0)
            keyHandler.onKeyUp(Constants.KEY_C)
            keyHandler.onKeyUp(Constants.KEY_3)
            keyHandler.onKeyUp(Constants.KEY_E)
            keyHandler.onKeyUp(Constants.KEY_6)
            keyHandler.onKeyUp(Constants.KEY_8)
            keyHandler.onKeyUp(Constants.KEY_1)
            keyHandler.onKeyUp(Constants.KEY_A)
            keyHandler.onKeyUp(Constants.KEY_B)
            keyHandler.onKeyUp(Constants.KEY_4)
            keyHandler.onKeyUp(Constants.KEY_5)
            keyHandler.onKeyUp(Constants.KEY_7)
            keyHandler.onKeyUp(Constants.KEY_D)
            keyHandler.onKeyUp(Constants.KEY_2)
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