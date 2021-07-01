package com.ekdorn.classicdungeon.shared.dependant.gl

import context
import org.khronos.webgl.Int8Array
import org.khronos.webgl.WebGLRenderingContext

actual object GLFunctions {
    actual fun setup (width: Int, height: Int) {
        context.viewport(0, 0, width, height)
        context.clearColor(0.0F, 0.0F, 0.0F, 1.0F)
        context.pixelStorei(WebGLRenderingContext.UNPACK_FLIP_Y_WEBGL, 1)
    }

    // TODO: extract buffer!
    actual fun drawElements (count: Int, indices: ByteArray) {
        context.clear(WebGLRenderingContext.DEPTH_BUFFER_BIT or WebGLRenderingContext.COLOR_BUFFER_BIT)
        val buff = context.createBuffer()
        context.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, buff)
        context.bufferData(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, Int8Array(indices.toTypedArray()), WebGLRenderingContext.STATIC_DRAW)
        context.drawElements(WebGLRenderingContext.TRIANGLES, count, WebGLRenderingContext.UNSIGNED_BYTE, 0)
        context.deleteBuffer(buff)
    }
}