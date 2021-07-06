package com.ekdorn.classicdungeon.shared.dependant.gl

import context
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLBuffer
import org.khronos.webgl.WebGLRenderingContext

actual class GLBuffer actual constructor (actual val size: Int) {
    private val self: WebGLBuffer? = context.createBuffer()


    actual fun bind () = context.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, self)

    actual fun fill (data: FloatArray) {
        context.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, self)
        context.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array(data.toTypedArray()), WebGLRenderingContext.DYNAMIC_DRAW)
    }

    actual fun release () {}

    actual fun delete () = context.deleteBuffer(self)
}
