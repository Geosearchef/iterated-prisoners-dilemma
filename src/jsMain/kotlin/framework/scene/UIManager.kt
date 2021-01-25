package framework.scene

import framework.ui.SceneUI

interface UIManager {
    fun regenerateUI(width: Int, height: Int)
    fun getUI() : SceneUI
}