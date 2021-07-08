package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.sqrt


internal data class Vector (var x: Float, var y: Float) {
    constructor (): this(0F, 0F)
    constructor (x: Int, y: Int): this(x.toFloat(), y.toFloat())

    inline var w: Int
        get () = x.toInt()
        set (v) { x = v.toFloat() }

    inline var h: Int
        get () = y.toInt()
        set (v) { y = v.toFloat() }

    operator fun rangeTo (other: Vector) = Vector(x - other.x, y - other.y)
    operator fun plus (other: Vector) = Vector(x + other.x, y + other.y)
    infix fun each (other: Vector) = Vector(x * other.x, y * other.y)

    fun length () = sqrt(x * x + y * y)
}
