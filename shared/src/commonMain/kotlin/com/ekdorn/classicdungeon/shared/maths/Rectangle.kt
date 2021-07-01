package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.abs

internal data class Rectangle (val left: Float, val top: Float, val right: Float, val bottom: Float) {
    fun width () = abs(left - right)
    fun height () = abs(top - bottom)

    fun toPointsArray () = floatArrayOf(left, top, right, top, right, bottom, left, bottom)
}
