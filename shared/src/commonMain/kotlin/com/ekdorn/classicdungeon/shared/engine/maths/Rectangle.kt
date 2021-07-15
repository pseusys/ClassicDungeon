package com.ekdorn.classicdungeon.shared.engine.maths

import kotlin.math.abs

// TODO: revise width + height
internal open class Rectangle (var left: Float, var top: Float, var right: Float, var bottom: Float) {
    constructor (left: Int, top: Int, right: Int, bottom: Int): this(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

    inline var width: Float
        get () = abs(left - right)
        set (v) { right = left + if (right > left) v else -v }

    inline var height: Float
        get () = abs(top - bottom)
        set (v) { bottom = top + if (bottom > top) v else -v }


    inline var coords: Vector
        get () = Vector(left, top)
        set (v) { left = v.x; top = v.y }

    inline var metrics: Vector
        get () = Vector(width, height)
        set (v) { width = v.x; height = v.y }


    inline var horizontal: Vector
        get () = Vector(left, right)
        set (v) { left = v.x; right = v.y }

    inline var vertical: Vector
        get () = Vector(top, bottom)
        set (v) { top = v.x; bottom = v.y }


    inline val ratio: Float
        get () = width / height


    fun translate (x: Float = 0F, y: Float = 0F) = apply {
        left += x
        right += x
        top += y
        bottom += y
    }

    fun toPointsArray () = floatArrayOf(left, top, right, top, right, bottom, left, bottom)

    override fun toString() =  "Rectangle: (left: $left; top: $top; right: $right; bottom: $bottom)"
}
