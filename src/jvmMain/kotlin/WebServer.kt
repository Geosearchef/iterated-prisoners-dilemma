import spark.kotlin.Http
import spark.kotlin.ignite
import util.Util
import java.io.File

object WebServer {
    val STATIC_FILES_LOCATION =  System.getProperty("user.dir") + File.separator + (if(Util.isRunningFromJar()) "static" else "build/distributions")

    val log = Util.logger()
    lateinit var http: Http

    fun init() {
        http = ignite()

        log.info("Serving static files from $STATIC_FILES_LOCATION")
        http.staticFiles.externalLocation(STATIC_FILES_LOCATION)
        http.port(CardSimulatorOptions.STATIC_PORT)

        http.get("/someRoute") {
            "Hello World from someRoute"
        }
    }

}