package com.ekdorn.classicdungeon.shared.dependant.gl

import org.khronos.webgl.WebGLRenderingContext


actual object GLFunctions {
    lateinit var context: WebGLRenderingContext

    actual fun setup () {
        context.clearColor(0.0F, 0.0F, 0.0F, 1.0F)
        context.pixelStorei(WebGLRenderingContext.UNPACK_FLIP_Y_WEBGL, 1)
        context.enable(WebGLRenderingContext.SCISSOR_TEST)
        context.enable(WebGLRenderingContext.BLEND)
        context.blendFunc(WebGLRenderingContext.SRC_ALPHA, WebGLRenderingContext.ONE_MINUS_SRC_ALPHA)
    }

    actual fun portal (width: Int, height: Int) {
        context.viewport(0, 0, width, height)
        context.scissor(0, 0, width, height)
    }

    actual fun clear () = context.clear(WebGLRenderingContext.COLOR_BUFFER_BIT)

    actual fun drawElements (count: Int) =
        context.drawElements(WebGLRenderingContext.TRIANGLES, count, WebGLRenderingContext.UNSIGNED_SHORT, 0)
}