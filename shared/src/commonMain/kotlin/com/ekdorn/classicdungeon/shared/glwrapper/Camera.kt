package com.ekdorn.classicdungeon.shared.glwrapper

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

    override suspend fun gameStarted (screenWidth: Int, screenHeight: Int) {
        calibrate(screenWidth, screenHeight)
        Input.onResized.add {
            calibrate(it.w, it.h)
            false
        }
    }

    override suspend fun gameEnded () {}
}