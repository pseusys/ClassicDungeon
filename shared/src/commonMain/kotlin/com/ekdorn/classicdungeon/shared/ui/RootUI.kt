package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal class RootUI (rect: Rectangle, screenWidth: Int, screenHeight: Int): LayoutUI(hashMapOf<String, Any>()) {
    init {
        coords = Vector(rect.left, rect.top)
        metrics = Vector(screenWidth, screenHeight)
    }

    fun resize (screenWidth: Int, screenHeight: Int) {
        metrics = Vector(screenWidth, screenHeight)
    }

    override fun updateVertices () {}
}
