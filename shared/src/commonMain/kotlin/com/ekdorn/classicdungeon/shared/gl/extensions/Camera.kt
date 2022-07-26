package com.ekdorn.classicdungeon.shared.gl.extensions

import com.ekdorn.classicdungeon.shared.IO
import com.ekdorn.classicdungeon.shared.engine.general.Assigned
import com.ekdorn.classicdungeon.shared.gl.primitives.Matrix

/**
 * Object, representing camera.
 * By default it scales pixels to GL units 1:1, however it may be scaled.
 */
internal object Camera: Assigned {
    /**
     * UI camera - doesn't support moving or scaling.
     * The only way to scale an object is using *pixelation* property.
     */
    val UI = Matrix()

    /**
     * Function, calibrating cameras.
     * @param width screen width in pixels (the same as camera width in GL units)
     * @param height screen height in pixels (the same as camera height in GL units)
     */
    private fun calibrate (width: Int, height: Int) {
        UI.toIdentity()
        UI.translate(-1F, 1F)
        UI.scale(2 / width.toFloat(), 2 / height.toFloat())
    }

    /**
     * Function, called once the game started, includes camera calibration.
     */
    override fun gameStarted () = IO.resizeEvents.add { calibrate(it.w, it.h) }
}