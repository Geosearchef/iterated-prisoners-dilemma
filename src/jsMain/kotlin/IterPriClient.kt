import api.ApiAccessor
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams
import websocket.WebsocketClient

/**
 * Cross framework.scene main controller
 */
object IterPriClient {
    fun init() {
        loadUsername()

        WebsocketClient.init()
        ApiAccessor.init()
    }

    private fun loadUsername() {
        username = URLSearchParams(window.location.search).get("username") ?: ""
        if (username.isBlank()) {
            window.location.href = "/"
        }
    }


    private var _username: String? = null
    var username: String
        get() = _username ?: ""
        set(value) {
            _username = value
        }
}