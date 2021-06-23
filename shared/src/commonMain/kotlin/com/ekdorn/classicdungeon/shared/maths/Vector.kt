package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.sqrt


internal data class Vector (var x: Double, var y: Double) {
    constructor (): this(0.0, 0.0)
    constructor (x: Int, y: Int): this(x.toDouble(), y.toDouble())

    inline var w: Int
        get () = x.toInt()
        set (v) { x = v.toDouble() }

    inline var h: Int
        get () = y.toInt()
        set (v) { y = v.toDouble() }

    fun rangeTo (other: Vector) = Vector(x - other.x, y - other.y)
    fun length () = sqrt(x * x + y * y)
}
