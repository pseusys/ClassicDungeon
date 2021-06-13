package com.ekdorn.classicdungeon.shared.maths

internal class Color private constructor (private val lights: DoubleArray) {
    constructor (a: Double, b: Double, c: Double, d: Double): this(doubleArrayOf(a, b, c, d))
    constructor (): this(0.0, 0.0, 0.0, 0.0)

    inline var r: Double
        get () = lights[0]
        set (v) { lights[0] = v }

    inline var g: Double
        get () = lights[1]
        set (v) { lights[1] = v }

    inline var b: Double
        get () = lights[2]
        set (v) { lights[2] = v }

    inline var a: Double
        get () = lights[3]
        set (v) { lights[3] = v }


    private fun offset (index: Int) = (3 - index) * 8

    inline var int: Int
        get () = lights.foldIndexed(0) { index, current, value -> current or ((value * 255).toInt() and 0xFF shl offset(index)) }
        set (v): Unit = lights.forEachIndexed { index, _ -> lights[index] = (v shr offset(index) and 0xFF).toDouble() / 255 }

}
