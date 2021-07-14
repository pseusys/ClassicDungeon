package com.ekdorn.classicdungeon.shared.engine.glextensions

import com.ekdorn.classicdungeon.shared.Input
import com.ekdorn.classicdungeon.shared.engine.generics.Assigned
import com.ekdorn.classicdungeon.shared.engine.maths.Matrix

internal object Camera: Assigned {
    val UI = Matrix()

    private fun calibrate (width: Int, height: Int) {
        UI.toIdentity()
        UI.translate(-1F, 1F)
        UI.scale(2 / width.toFloat(), 2 / height.toFloat())
    }

    override fun gameStarted () = Input.onResized.add {
        calibrate(it.w, it.h)
        false
    }
}