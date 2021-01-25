import kotlinx.browser.document

fun main() {
    console.log("Loading card simulator client")

    document.addEventListener("DOMContentLoaded", {
        CardSimulatorClient.init()
    })
}
