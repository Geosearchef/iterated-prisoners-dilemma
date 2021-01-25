package framework.rendering

import CardSimulatorClient

class Image(val imageSrc: String) {

    var loaded = false
        private set

    var wrappedImage = org.w3c.dom.Image()

    init {
        wrappedImage.src = imageSrc
        wrappedImage.onload = { this.onImageLoaded() }
    }

    fun onImageLoaded() {
        console.log("framework.rendering.Image loaded: $imageSrc")
        loaded = true

        CardSimulatorClient.requestRender()
    }
}