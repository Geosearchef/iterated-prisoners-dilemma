import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.url.URLSearchParams
import websocket.WebsocketClient


fun main() {
    window.onload = {
        console.log("Loading cards simulator...")

        val username = URLSearchParams(window.location.search).get("username")
        if(username != null && username.isNotBlank()) {
            (document.getElementById("testLabel") as HTMLDivElement).textContent = "My username is $username"
        } else {
            window.location.href = "/"
        }

        WebsocketClient.init()
    }
}
