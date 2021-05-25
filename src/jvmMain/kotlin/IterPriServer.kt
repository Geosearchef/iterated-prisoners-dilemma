import api.Api
import org.slf4j.LoggerFactory
import websocket.WebsocketServer

fun main() {
    System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
    val log = LoggerFactory.getLogger("main")

    log.info("Starting cards server")
    log.info("Ports used: Static: ${IterPriOptions.STATIC_PORT}, API: ${IterPriOptions.API_PORT}, WebSocket: ${IterPriOptions.WEBSOCKET_PORT}")

    WebServer.init()
    WebsocketServer.init()
    Api.init()

    // just serve /static
    // script at /static/output.js


}