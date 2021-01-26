package framework.ui

import org.w3c.dom.CanvasRenderingContext2D

interface UIComponent {
    fun render(ctx: CanvasRenderingContext2D)
}