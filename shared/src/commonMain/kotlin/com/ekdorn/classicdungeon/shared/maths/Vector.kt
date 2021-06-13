package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.sqrt


internal class Vector private constructor (private val vertexes: DoubleArray) {
    constructor (a: Double, b: Double): this(doubleArrayOf(a, b))
    constructor (): this(0.0, 0.0)
    constructor (a: Int, b: Int): this(doubleArrayOf(a.toDouble(), b.toDouble()))

    inline var x: Double
        get () = vertexes[0]
        set (v) { vertexes[0] = v }

    inline var y: Double
        get () = vertexes[1]
        set (v) { vertexes[1] = v }

    inline var w: Int
        get () = vertexes[0].toInt()
        set (v) { vertexes[0] = v.toDouble() }

    inline var h: Int
        get () = vertexes[1].toInt()
        set (v) { vertexes[1] = v.toDouble() }

    fun rangeTo (other: Vector) = Vector(x - other.x, y - other.y)
    fun length () = sqrt(x * x + y * y)
}
