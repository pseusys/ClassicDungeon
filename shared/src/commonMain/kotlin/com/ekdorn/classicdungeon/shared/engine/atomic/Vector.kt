package com.ekdorn.classicdungeon.shared.engine.atomic

import kotlinx.serialization.Serializable
import kotlin.math.sqrt


/**
 * Class, representing vector - a pair of floats, 2D coords.
 */
@Serializable
internal data class Vector (var x: Float, var y: Float) {
    constructor (): this(0F, 0F)
    constructor (x: Int, y: Int): this(x.toFloat(), y.toFloat())

    /**
     * Integer X coordinate.
     */
    inline var w: Int
        get () = x.toInt()
        set (v) { x = v.toFloat() }

    /**
     * Integer Y coordinate.
     */
    inline var h: Int
        get () = y.toInt()
        set (v) { y = v.toFloat() }

    /**
     * Inline ratio - width X by Y.
     */
    inline val ratio: Float
        get () = x / y

    /**
     * Vector + Vector operations.
     */
    operator fun minus (other: Vector) = Vector(x - other.x, y - other.y)
    operator fun plus (other: Vector) = Vector(x + other.x, y + other.y)
    operator fun times (other: Vector) = Vector(x * other.x, y * other.y)
    operator fun div (other: Vector) = Vector(x / other.x, y / other.y)

    /**
     * Vector + Float operations.
     */
    operator fun plus (oper: Float) = Vector(x + oper, y + oper)
    operator fun times (oper: Float) = Vector(x * oper, y * oper)
    operator fun div (oper: Float) = Vector(x / oper, y / oper)

    /**
     * Vector length.
     */
    fun length () = sqrt(x * x + y * y)
}
