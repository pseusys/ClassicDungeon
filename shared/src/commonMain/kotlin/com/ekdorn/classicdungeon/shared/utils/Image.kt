package com.ekdorn.classicdungeon.shared.utils

import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Rectangle

internal class Image (width: Int, height: Int, val pixels: ByteArray): Rectangle(0, 0, width, height) {
    fun getPixel (x: Int, y: Int): Color {
        val start = (y * width.toInt() + x) * 4
        return Color(pixels[start], pixels[start + 1], pixels[start + 2], pixels[start + 3])
    }
}
