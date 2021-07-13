package com.ekdorn.classicdungeon.shared.lib

import com.ekdorn.classicdungeon.shared.maths.Vector


/**
 * Resize-Dependant Property
 */
internal class RDP (val value: Vector, private var xLambda: (Float) -> Float, private var yLambda: (Float) -> Float) {
    private var idealValue = value.copy()
    private var listening = true

    fun setIdeal (ideal: Vector, defaultX: Float, defaultY: Float) {
        if ((ideal.x == -1F) && (ideal.y == -1F)) {
            value.apply { x = defaultX; y = defaultY }
            listening = false
        } else idealValue = ideal
    }

    fun resize (ratio: Float) {
        if (listening) {
            val newWidth = idealValue.y * xLambda(ratio)
            val newHeight = idealValue.x * yLambda(ratio)
            if (idealValue.x == -1F) value.x = newWidth
            else if (idealValue.y == -1F) value.y = newHeight
            else {
                if (newWidth <= idealValue.x) {
                    value.x = newWidth
                    value.y = idealValue.y
                } else {
                    value.x = idealValue.x
                    value.y = newHeight
                }
            }
        }
    }
}
