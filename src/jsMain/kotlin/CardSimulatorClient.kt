import api.ApiAccessor
import game.Game
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.url.URLSearchParams
import rendering.Rendering
import util.Util
import websocket.WebsocketClient

object CardSimulatorClient {

    val canvas = document.getElementById("table-canvas") as? HTMLCanvasElement ?: throw RuntimeException("Table canvas not found")

    fun init() {
        console.log("Loading card simulator...")

        loadUsername()

        WebsocketClient.init()
        ApiAccessor.init()
        Game.init()

        window.requestAnimationFrame(CardSimulatorClient::animationFrame)
    }

    private fun loadUsername() {
        username = URLSearchParams(window.location.search).get("username") ?: ""
        if (username.isBlank()) {
            window.location.href = "/"
        }
    }


    var renderRequested = true
    var continousRendering = false

    fun requestRender() {
        renderRequested = true
    }

    private var lastFrame = Util.currentTimeMillis()
    fun animationFrame(t: Double) {
        val deltaMillis = Util.currentTimeMillis() - lastFrame;
        lastFrame += deltaMillis;
        val delta = deltaMillis / 1000.0;

        Rendering.render(delta, canvas)

        window.requestAnimationFrame(CardSimulatorClient::animationFrame)
    }


    private var _username: String? = null
    var username: String
        get() = _username ?: ""
        set(value) {
            _username = value
        }
}

fun main() {
    window.onload = { CardSimulatorClient.init() }
}
