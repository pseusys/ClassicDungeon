package com.ekdorn.classicdungeon.shared.glextensions

import com.ekdorn.classicdungeon.shared.Input
import com.ekdorn.classicdungeon.shared.generics.Assigned
import com.ekdorn.classicdungeon.shared.maths.Matrix

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