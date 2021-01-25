package rendering

import framework.rendering.color
import framework.rendering.fillRect
import framework.scene.Scene.SceneRenderer
import game.Game
import org.w3c.dom.CanvasRenderingContext2D

object Rendering : SceneRenderer {

    override fun render(ctx: CanvasRenderingContext2D) {
        ctx.color("#333333")
        ctx.fillRect(Game.someGameObject)
    }

}