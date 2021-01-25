import framework.scene.UIManager
import framework.ui.SceneUI
import util.math.Vector

class CardsUI(width: Int, height: Int) : SceneUI(width, height) {
    override fun isMouseEventOnUI(mousePosition: Vector): Boolean {
        return false
    }
}

/**
 * can be integrated into framework.scene "manager"
 */
object CardsUIManager : UIManager {

    var uiInstance = CardsUI(300, 200)

    override fun regenerateUI(width: Int, height: Int) {
        uiInstance = CardsUI(width, height)
    }

    override fun getUI(): SceneUI = uiInstance
}