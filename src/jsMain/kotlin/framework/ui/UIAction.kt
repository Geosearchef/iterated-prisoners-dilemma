package framework.ui

interface UIAction {
    val representation: String
    fun onAction()
}