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


    inline var int: Int
        get () = ((r * 255).toInt() and 0xFF shl 24) or
                ((g * 255).toInt() and 0xFF shl 16) or
                ((b * 255).toInt() and 0xFF shl 8) or
                ((a * 255).toInt() and 0xFF)
        set (v) {
            r = (v shr 24 and 0xFF).toDouble() / 255
            g = (v shr 16 and 0xFF).toDouble() / 255
            b = (v shr 8 and 0xFF).toDouble() / 255
            a = (v and 0xFF).toDouble() / 255
        }


    inline var bytes: ByteArray
        get () = lights.map { value -> (value * 255).toInt().toByte() }.toByteArray()
        set (v): Unit = lights.forEachIndexed { index, _ -> lights[index] = v[index].toDouble() / 255 }
}
