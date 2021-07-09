package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.abs

// TODO: revise width + height
internal data class Rectangle (var left: Float, var top: Float, var right: Float, var bottom: Float) {
    constructor (left: Int, top: Int, right: Int, bottom: Int): this(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

    fun width () = abs(left - right)
    fun height () = abs(top - bottom)

    fun toPointsArray () = floatArrayOf(left, top, right, top, right, bottom, left, bottom)
}
