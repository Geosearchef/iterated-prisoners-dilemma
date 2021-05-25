import kotlinx.browser.document

fun main() {
    console.log("Loading")

    document.addEventListener("DOMContentLoaded", {
        IterPriClient.init()
    })
}
