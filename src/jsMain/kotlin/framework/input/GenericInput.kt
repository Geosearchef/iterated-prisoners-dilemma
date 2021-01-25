package framework.input

import framework.scene.SceneInput
import framework.scene.SceneManager
import kotlinx.browser.window
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent

/**
 * Input for all framework.scene
 */
object GenericInput : SceneInput() {
    const val KEY_A = 65

    private fun onMouseMove(event: Event) {
        if(event !is MouseEvent) throw RuntimeException("Event of wrong type")
        SceneManager.currentScene.input.sealedOnMouseMove(event)
    }

    private fun onMouseDown(event: Event) {
        if (event !is MouseEvent) throw RuntimeException("Event of wrong type")
        SceneManager.currentScene.input.sealedOnMouseDown(event)
    }

    private fun onMouseUp(event: Event) {
        if (event !is MouseEvent) throw RuntimeException("Event of wrong type")
        SceneManager.currentScene.input.sealedOnMouseUp(event)
    }

    private fun onMouseWheel(event: Event) {
        if (event !is WheelEvent) throw RuntimeException("Event of wrong type")
        SceneManager.currentScene.input.sealedOnMouseWheel(event)
    }

    private fun onKeyDown(event: Event) {
        if (event !is KeyboardEvent) throw RuntimeException("Event of wrong type")

        SceneManager.currentScene.input.onKeyDown(event)
    }

    private fun onKeyUp(event: Event) {
        if (event !is KeyboardEvent) throw RuntimeException("Event of wrong type")

        SceneManager.currentScene.input.onKeyUp(event)
    }

    fun init(canvas: HTMLCanvasElement) {
        with(canvas) {
            addEventListener("contextmenu", Event::preventDefault)
            addEventListener("mousemove", GenericInput::onMouseMove)
            addEventListener("mousedown", GenericInput::onMouseDown)
            addEventListener("mouseup", GenericInput::onMouseUp)
            addEventListener("wheel", GenericInput::onMouseWheel)
        }
        with(window) {
            addEventListener("keydown", GenericInput::onKeyDown)
            addEventListener("keyup", GenericInput::onKeyUp)
        }
    }
}