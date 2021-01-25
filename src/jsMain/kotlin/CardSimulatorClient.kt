import api.ApiAccessor
import game.Game
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event
import org.w3c.dom.url.URLSearchParams
import websocket.WebsocketClient

fun init(event: Event) {
    console.log("Loading card simulator...")

    Game.username = URLSearchParams(window.location.search).get("username") ?: ""
    if(Game.username.isNotBlank()) {
        (document.getElementById("testLabel") as HTMLDivElement).textContent = "My username is ${Game.username}"
    } else {
        window.location.href = "/"
        return
    }

    WebsocketClient.init()
    ApiAccessor.init()
    Game.init()
}

fun main() {
    window.onload = { init(it) }
}
