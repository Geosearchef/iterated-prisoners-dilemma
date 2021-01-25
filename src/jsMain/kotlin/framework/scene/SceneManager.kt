package framework.scene

import CardSimulatorClient
import framework.input.GenericInput
import kotlinx.browser.window

object SceneManager {
    private val _scenes: MutableList<Scene> = ArrayList()
    val scenes: List<Scene> get() = _scenes


    lateinit var currentScene: Scene

    fun init(defaultScene: Scene) {
        currentScene = defaultScene
        scenes.forEach { it.initFunction() }
        GenericInput.init(CardSimulatorClient.canvas)

        window.requestAnimationFrame(CardSimulatorClient::animationFrame)
    }

    fun register(scene: Scene) {
        _scenes.add(scene)
    }
}