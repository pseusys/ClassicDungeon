package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal class LayoutUI (rect: Rectangle) : WidgetUI(rect) {
    val children = mutableListOf<WidgetUI>()
    var pixelCoords = Vector(rect.left, rect.top)
    var pixelMetrics = Vector(rect.right, rect.bottom)

    override fun update (elapsed: Float) {
        children.forEach { if (it.exists && it.visible) it.update(elapsed) }
    }

    override fun draw () {
        children.forEach { if (it.exists && it.visible) it.draw() }
    }

    override fun delete() {}
}
