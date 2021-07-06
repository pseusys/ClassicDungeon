package com.ekdorn.classicdungeon.shared.maths

// TODO: replace color with array
internal data class Color (var r: Float, var g: Float, var b: Float, var a: Float) {
    constructor (): this(0F, 0F, 0F, 0F)

    inline var int: Int
        get () = ((r * 255).toInt() and 0xFF shl 24) or ((g * 255).toInt() and 0xFF shl 16) or ((b * 255).toInt() and 0xFF shl 8) or ((a * 255).toInt() and 0xFF)
        set (v) {
            r = (v shr 24 and 0xFF).toFloat() / 255
            g = (v shr 16 and 0xFF).toFloat() / 255
            b = (v shr 8 and 0xFF).toFloat() / 255
            a = (v and 0xFF).toFloat() / 255
        }

    inline var bytes: ByteArray
        get () = byteArrayOf((r * 255).toInt().toByte(), (g * 255).toInt().toByte(), (b * 255).toInt().toByte(), (a * 255).toInt().toByte())
        set (v) {
            r = v[0].toFloat() / 255
            g = v[1].toFloat() / 255
            b = v[2].toFloat() / 255
            a = v[3].toFloat() / 255
        }
}
