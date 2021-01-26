package input

import framework.scene.SceneInput
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent

object Input : SceneInput() {

    var isButtonDown = false

    override fun onMouseMove(event: MouseEvent, isOnUI: Boolean) {

    }

    override fun onMouseDown(event: MouseEvent, isOnUI: Boolean) {
        if (event.button.toInt() == 0) {
            isButtonDown = true
        }
    }

    override fun onMouseUp(event: MouseEvent, isOnUI: Boolean) {
        if (event.button.toInt() == 0) {
            isButtonDown = false
        }
    }

    override fun onMouseWheel(event: WheelEvent, isOnUI: Boolean) {
        super.onMouseWheel(event, isOnUI)
    }

    override fun onKeyDown(event: KeyboardEvent) {
        super.onKeyDown(event)
    }

    override fun onKeyUp(event: KeyboardEvent) {
        super.onKeyUp(event)
    }
}