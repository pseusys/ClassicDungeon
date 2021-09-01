package com.ekdorn.classicdungeon.shared.gl.primitives

import com.ekdorn.classicdungeon.shared.engine.atomic.Color
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector


internal class Image (width: Int, height: Int, val pixels: ByteArray) {
    val metrics = Vector(width, height)

    fun getPixel (x: Int, y: Int): Color {
        val start = (y * metrics.w + x) * 4
        return Color(pixels.sliceArray(start until start + 4))
    }
}
