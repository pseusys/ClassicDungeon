package com.ekdorn.classicdungeon.shared.utils

import com.ekdorn.classicdungeon.shared.maths.Color

internal class Image (val width: Int, val height: Int, val pixels: ByteArray) {
    fun getPixel (x: Int, y: Int): Color {
        val start = (y * width + x) * 4
        return Color(pixels[start], pixels[start + 1], pixels[start + 2], pixels[start + 3])
    }
}
