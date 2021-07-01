package com.ekdorn.classicdungeon.shared.glwrapper

import com.ekdorn.classicdungeon.shared.dependant.gl.GLTexture
import com.ekdorn.classicdungeon.shared.maths.Color


internal class GradientTexture: GLTexture() {
    fun image (width: Int, height: Int, pixels: Array<Color>) {
        val arrays = pixels.map { it.bytes }
        image(width, height, ByteArray(pixels.size * 4) { index -> arrays[index / 4][index % 4] })
    }
}
