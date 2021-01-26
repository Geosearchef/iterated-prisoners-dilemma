package game

import kotlinx.browser.document
import kotlinx.dom.clear
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement

object SeatsView {
    val seatsListDiv = document.getElementById("seats-list") as HTMLDivElement

    fun init() {
        recreate()
    }

    fun recreate() {
        seatsListDiv.clear()

        Game.gameInfo.seats.forEach {
            seatsListDiv.append(createSeatEntry(it.color,  it.id,null))
        }
    }

    private fun createSeatEntry(color: String, id: Int, playerName: String?): HTMLDivElement {
        val seatDiv = (document.createElement("div") as HTMLDivElement).apply {
            style.display = "block"
        }
        val seatButton = (document.createElement("input") as HTMLInputElement).apply {
            type = "button"
            value = playerName ?: "< Join >"
            style.backgroundColor = color
            name = id.toString()
        }
        seatDiv.appendChild(seatButton)

        return seatDiv
    }
}