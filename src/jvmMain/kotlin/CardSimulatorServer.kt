import api.Api
import org.slf4j.LoggerFactory
import websocket.WebsocketServer

fun main() {
    System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
    val log = LoggerFactory.getLogger("main")

    log.info("Starting cards server")
    log.info("Ports used: Static: ${CardSimulatorOptions.STATIC_PORT}, API: ${CardSimulatorOptions.API_PORT}, WebSocket: ${CardSimulatorOptions.WEBSOCKET_PORT}")

    WebServer.init()

    Api.init()
    WebsocketServer.init()

    // just serve /static
    // script at /static/output.js


}