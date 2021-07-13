package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.abs

// TODO: revise width + height
internal open class Rectangle (var left: Float, var top: Float, var right: Float, var bottom: Float) {
    constructor (left: Int, top: Int, right: Int, bottom: Int): this(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

    inline var width: Float
        get () = abs(left - right)
        set (v) {
            right = left + if (right > left) v else -v
        }

    inline var height: Float
        get () = abs(top - bottom)
        set (v) {
            bottom = top + if (bottom > top) v else -v
        }


    inline val ratio: Float
        get () = width / height


    fun toPointsArray () = floatArrayOf(left, top, right, top, right, bottom, left, bottom)

    override fun toString() =  "Rectangle: (left: $left; top: $top; right: $right; bottom: $bottom)"
}
