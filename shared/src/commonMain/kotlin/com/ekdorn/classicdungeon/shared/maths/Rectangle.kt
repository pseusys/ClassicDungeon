package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.abs

internal data class Rectangle (var left: Float, var top: Float, var right: Float, var bottom: Float) {
    fun width () = abs(left - right)
    fun height () = abs(top - bottom)

    fun toPointsArray () = floatArrayOf(left, top, right, top, right, bottom, left, bottom)
}
