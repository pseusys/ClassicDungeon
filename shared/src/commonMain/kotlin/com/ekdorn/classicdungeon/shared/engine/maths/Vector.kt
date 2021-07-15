package com.ekdorn.classicdungeon.shared.engine.maths

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

    inline val ratio: Float
        get () = x / y

    operator fun rangeTo (other: Vector) = Vector(x - other.x, y - other.y)
    operator fun plus (other: Vector) = Vector(x + other.x, y + other.y)
    operator fun times (other: Vector) = Vector(x * other.x, y * other.y)
    operator fun div (other: Vector) = Vector(x / other.x, y / other.y)

    operator fun plus (oper: Float) = Vector(x + oper, y + oper)
    operator fun times (oper: Float) = Vector(x * oper, y * oper)
    operator fun div (oper: Float) = Vector(x / oper, y / oper)

    fun length () = sqrt(x * x + y * y)
}
