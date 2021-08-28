package com.ekdorn.classicdungeon.shared.gl.extensions

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLTexture
import com.ekdorn.classicdungeon.shared.engine.maths.Color


internal class GradientTexture: GLTexture() {
    fun image (width: Int, height: Int, pixels: Array<Color>) {
        val arrays = pixels.map { it.bytes }
        image(width, height, ByteArray(pixels.size * 4) { index -> arrays[index / 4][index % 4] })
    }
}
