package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal abstract class LayoutUI (rect: Rectangle): ElementUI(rect) {
    private val children = mutableListOf<ElementUI>()

    fun add (element: ElementUI) {
        element.translate(pixelCoords, pixelMetrics)
        element.parent = this
        children.add(element)
    }

    fun remove (element: ElementUI) {
        element.parent = null
        children.remove(element)
    }

    override fun translate (parentCoords: Vector, parentMetrics: Vector) {
        super.translate(parentCoords, parentMetrics)
        children.forEach { if (it.exists && it.visible) it.translate(pixelCoords, pixelMetrics) }
    }

    override fun update (elapsed: Int) {
        super.update(elapsed)
        children.forEach { if (it.exists && it.visible) {
            it.translate(pixelCoords, pixelMetrics)
            it.update(elapsed)
        } }
    }

    final override fun draw () {
        super.draw()
        drawSelf()
        children.forEach { if (it.exists && it.visible) it.draw() }
    }
    abstract fun drawSelf ()
}
