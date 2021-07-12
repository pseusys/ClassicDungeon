package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal class RootUI (rect: Rectangle, screenWidth: Int, screenHeight: Int): LayoutUI(rect) {
    init {
        pixelCoords = Vector(rect.left, rect.top)
        pixelMetrics = Vector(screenWidth, screenHeight)
    }

    fun resize (screenWidth: Int, screenHeight: Int) {
        pixelMetrics = Vector(screenWidth, screenHeight)
    }

    override fun resize(ratio: Float) {}

    override fun updateVertices() {}
}
