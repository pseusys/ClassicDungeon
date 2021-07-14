package com.ekdorn.classicdungeon.shared.engine.glextensions

import com.ekdorn.classicdungeon.shared.dependant.gl.GLTexture
import com.ekdorn.classicdungeon.shared.engine.maths.Color


internal class GradientTexture: GLTexture() {
    fun image (width: Int, height: Int, pixels: Array<Color>) {
        val arrays = pixels.map { it.bytes }
        image(width, height, ByteArray(pixels.size * 4) { index -> arrays[index / 4][index % 4] })
    }
}
