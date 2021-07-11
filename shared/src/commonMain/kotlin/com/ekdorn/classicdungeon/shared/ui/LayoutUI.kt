package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal class LayoutUI (rect: Rectangle): WidgetUI(Rectangle (0F, 0F, 0F, 0F)) {
    val children = mutableListOf<WidgetUI>()

    // TODO: refactor!
    var pixelCoords = Vector(rect.left, rect.top)
        private set
    var pixelMetrics = Vector(rect.right, rect.bottom)
        private set

    fun resize (pixelWidth: Int, pixelHeight: Int) {
        pixelMetrics = Vector(pixelWidth, pixelHeight)
        children.forEach { if (it.exists && it.visible && it is PreservingUI) { it.parentalResize(pixelMetrics.ratio) } }
    }

    override fun update (elapsed: Int) {
        children.forEach { if (it.exists && it.visible) it.update(elapsed) }
    }

    override fun draw () {
        GLFunctions.clear() // TODO: look where it was cleared!
        children.forEach { if (it.exists && it.visible) it.draw() }
    }

    override fun delete() {}
}
