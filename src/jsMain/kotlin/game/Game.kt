package game

import AllScoresInfoMessage
import GameInfo
import Message
import OwnScoreInfoMessage
import PlayerListInfoMessage
import RoundInfoMessage
import ServerLoginMessage
import TimerInfoMessage
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLParagraphElement
import util.Util
import kotlin.math.max
import kotlin.math.round

object Game {

    lateinit var gameInfo: GameInfo

    val playerListLabel = document.getElementById("player-list") as HTMLParagraphElement
    val timerLabel = document.getElementById("timer-label") as HTMLHeadingElement
    val roundLabel = document.getElementById("round-label") as HTMLHeadingElement
    val scoreLabel = document.getElementById("score-label") as HTMLHeadingElement
    val playerScoreList = document.getElementById("player-score-list") as HTMLParagraphElement

    var playerList = emptyArray<String>()
    var round: Int = 0
    var ownScore: Int = 0
    var allScores: Map<String, Int> = mapOf()

    var nextTimerEnd = Util.currentTimeMillis()



    fun init() {
        updateTimer()
    }
    fun onServerMessage(msg: Message) {
        when(msg) {
            is ServerLoginMessage -> {
                console.log("Successfully logged in, got game info")

                gameInfo = msg.gameInfo
            }

            is PlayerListInfoMessage -> {
                console.log(("Got player list ${msg.players}"))
                playerList = msg.players
                playerListLabel.innerText = playerList.joinToString(", ")
            }

            is TimerInfoMessage -> {
                console.log("Got timer info, ${msg.timeLeft} ms remaining")
                nextTimerEnd = Util.currentTimeMillis() + msg.timeLeft
            }

            is RoundInfoMessage -> {
                console.log("Got round info, round: ${msg.round}")
                round = msg.round
                roundLabel.innerText = "Opponent ${round + 1} / ${playerList.size}"
            }

            is OwnScoreInfoMessage -> {
                console.log("Got own score: ${msg.score}")
                ownScore = msg.score
                scoreLabel.innerText = "Score: $ownScore"
            }

            is AllScoresInfoMessage -> {
                allScores = msg.scores
                updateScoreboard()
            }
        }
    }

    fun updateScoreboard() {
        playerScoreList.innerText = allScores.entries.sortedByDescending { it.value }
            .map { "${it.key}: ${it.value}" }
            .joinToString("<br>")
    }

    fun updateTimer() {
        timerLabel.innerText = "${max(0.0, round((nextTimerEnd - Util.currentTimeMillis()) / 1000.0))} s"

        window.setTimeout({ updateTimer() }, 200)
    }
}