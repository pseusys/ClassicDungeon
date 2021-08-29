package com.ekdorn.classicdungeon.shared.engine.utils

import com.ekdorn.classicdungeon.shared.engine.maths.Color
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle


internal class Image (width: Int, height: Int, val pixels: ByteArray): Rectangle(0, 0, width, height) {
    fun getPixel (x: Int, y: Int): Color {
        val start = (y * width.toInt() + x) * 4
        return Color(pixels.sliceArray(start until start + 4))
    }
}
