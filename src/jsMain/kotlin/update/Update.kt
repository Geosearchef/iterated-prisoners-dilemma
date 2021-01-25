package update

import framework.scene.Scene
import game.Game
import input.Input
import util.math.Vector

import util.math.times

object Update : Scene.SceneUpdate {
    override fun update(delta: Double) {
        if (!Input.isButtonDown) {
            Game.someGameObject.pos += delta * Vector(50.0, 20.0)
        }
    }
}