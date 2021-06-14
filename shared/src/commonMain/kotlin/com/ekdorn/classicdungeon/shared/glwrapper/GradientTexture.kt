package com.ekdorn.classicdungeon.shared.glwrapper

import com.ekdorn.classicdungeon.shared.maths.Color


internal class GradientTexture: Texture() {
    fun fill (width: Int, height: Int, pixels: Array<Color>) {
        val arrays = pixels.map { it.bytes }
        fill(width, height, ByteArray(pixels.size * 4) { index -> arrays[index / 4][index % 4] })
    }
}