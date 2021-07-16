package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle
import com.ekdorn.classicdungeon.shared.engine.maths.Vector

internal class RootUI (screenWidth: Int, screenHeight: Int): LayoutUI(hashMapOf<String, Any>()) {
    init {
        coords = Vector(0F, 0F)
        metrics = Vector(screenWidth, screenHeight)
        dimens = Vector(1F, 1F)
        verticalAlignment = ALIGNMENT.START
        horizontalAlignment = ALIGNMENT.START
    }



    fun enter (elapsed: Int) {
        GLFunctions.clear()
        translate(coords, metrics)
        update(elapsed)
        draw()
    }

    fun resize (screenWidth: Int, screenHeight: Int) {
        metrics = Vector(screenWidth, screenHeight)
    }
}
