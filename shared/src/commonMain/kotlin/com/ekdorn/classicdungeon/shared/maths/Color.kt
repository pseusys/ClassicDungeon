package com.ekdorn.classicdungeon.shared.maths

internal data class Color (var r: Double, var g: Double, var b: Double, var a: Double) {
    constructor (): this(0.0, 0.0, 0.0, 0.0)

    inline var int: Int
        get () = ((r * 255).toInt() and 0xFF shl 24) or ((g * 255).toInt() and 0xFF shl 16) or ((b * 255).toInt() and 0xFF shl 8) or ((a * 255).toInt() and 0xFF)
        set (v) {
            r = (v shr 24 and 0xFF).toDouble() / 255
            g = (v shr 16 and 0xFF).toDouble() / 255
            b = (v shr 8 and 0xFF).toDouble() / 255
            a = (v and 0xFF).toDouble() / 255
        }

    inline var bytes: ByteArray
        get () = byteArrayOf((r * 255).toInt().toByte(), (g * 255).toInt().toByte(), (b * 255).toInt().toByte(), (a * 255).toInt().toByte())
        set (v) {
            r = v[0].toDouble() / 255
            g = v[1].toDouble() / 255
            b = v[2].toDouble() / 255
            a = v[3].toDouble() / 255
        }
}
