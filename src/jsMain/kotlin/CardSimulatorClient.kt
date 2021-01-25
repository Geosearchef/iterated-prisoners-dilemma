import api.ApiAccessor
import framework.rendering.GenericRendering
import framework.scene.Scene
import framework.scene.SceneManager
import game.Game
import input.Input
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.url.URLSearchParams
import rendering.Rendering
import update.Update
import util.Util
import websocket.WebsocketClient

/**
 * Cross framework.scene main controller
 */
object CardSimulatorClient {
    // needs to be loaded automatically
    object DefaultScene : Scene(Input, Update, Rendering, CardsUIManager, initFunction = { Game.init() })

    val canvas = document.getElementById("table-canvas") as? HTMLCanvasElement ?: throw RuntimeException("Table canvas not found")

    fun init() {
        loadUsername()

        WebsocketClient.init()
        ApiAccessor.init()
        SceneManager.init(DefaultScene)

        window.requestAnimationFrame(CardSimulatorClient::animationFrame)
    }

    private fun loadUsername() {
        username = URLSearchParams(window.location.search).get("username") ?: ""
        if (username.isBlank()) {
            window.location.href = "/"
        }
    }


    var renderRequested = true
    var continousRendering = true // TODO: sure?

    fun requestRender() {
        renderRequested = true
    }

    private var lastFrame = Util.currentTimeMillis()
    fun animationFrame(t: Double) {
        val deltaMillis = Util.currentTimeMillis() - lastFrame;
        lastFrame += deltaMillis;
        val delta = deltaMillis / 1000.0;

        SceneManager.currentScene.update.update(delta)
        GenericRendering.render(delta, canvas)

        window.requestAnimationFrame(CardSimulatorClient::animationFrame)
    }


    private var _username: String? = null
    var username: String
        get() = _username ?: ""
        set(value) {
            _username = value
        }
}