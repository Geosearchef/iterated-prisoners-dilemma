package game.players

import org.eclipse.jetty.websocket.api.Session
import util.Util
import websocket.WebsocketServer.getRemoteHostAddress

object PlayerManager {
    val log = Util.logger()

    val players: MutableList<Player> = ArrayList()

    fun addPlayer(player: Player) { // broadcast to all others only happens on join / leave seat
        players.add(player)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    fun getPlayerBySession(session: Session): Player? = players.find { it.session == session }

    fun attemptLogin(username: String, session: Session): Boolean {
        if(username.isBlank()) {
            log.info("${session.getRemoteHostAddress()} attempted to use blank username")
            return false
        }
        synchronized(players) {
            if(players.none { it.username == username }) {
                log.info("${session.getRemoteHostAddress()} logged in as $username")
                addPlayer(Player(username, session))
                return true
            } else {
                log.info("${session.getRemoteHostAddress()} attempted to grab already taken username $username, disconnecting...")
                return false
            }
        }
    }

    fun onSessionDisconnected(session: Session) {
        // TODO: query this as task
        synchronized(players) {
            players.find { it.session == session }?.let {
                log.info("${it.username}(${session.getRemoteHostAddress()}) disconnected")
                removePlayer(it)
            }
        }

        // TODO: do sth
    }
}