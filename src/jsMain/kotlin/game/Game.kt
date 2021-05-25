package game

import GameInfo
import Message
import PlayerListInfoMessage
import ServerLoginMessage
import kotlinx.browser.document
import org.w3c.dom.HTMLParagraphElement

object Game {

    lateinit var gameInfo: GameInfo

    val playerListLabel = document.getElementById("player-list") as HTMLParagraphElement
    var playerList = emptyArray<String>()

    fun onServerMessage(msg: Message) {
        when(msg) {
            is ServerLoginMessage -> {
                console.log("Successfully logged in, got game info")

                gameInfo = msg.gameInfo
            }

            is PlayerListInfoMessage -> {
                console.log(("Got player list ${msg.players}"))
                playerList = msg.players
                playerListLabel.innerText = playerList.joinToString(",")
            }
        }
    }


    fun init() {

    }
}