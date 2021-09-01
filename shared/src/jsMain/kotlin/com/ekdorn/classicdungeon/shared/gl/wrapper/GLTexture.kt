package com.ekdorn.classicdungeon.shared.gl.wrapper

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions.context
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLTexture


actual open class GLTexture {
    actual enum class FILTERING (val mode: Int) {
        NEAREST(WebGLRenderingContext.NEAREST), LINEAR(WebGLRenderingContext.LINEAR)
    }

    actual enum class WRAPPING (val mode: Int) {
        REPEAT(WebGLRenderingContext.REPEAT),
        MIRROR(WebGLRenderingContext.MIRRORED_REPEAT),
        CLAMP(WebGLRenderingContext.CLAMP_TO_EDGE)
    }


    actual val id = 0
    private var self: WebGLTexture? = null

    init {
        generate()
    }


    actual fun generate () {
        self = context.createTexture()
        context.bindTexture(WebGLRenderingContext.TEXTURE_2D, self)
    }

    actual fun bind () = context.bindTexture(WebGLRenderingContext.TEXTURE_2D, self)

    actual fun release () {}

    actual fun delete () = context.deleteTexture(self)

    actual open fun filter (minification: FILTERING, magnification: FILTERING) {
        context.bindTexture(WebGLRenderingContext.TEXTURE_2D, self)
        context.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MIN_FILTER, minification.mode)
        context.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MAG_FILTER, magnification.mode)
    }

    actual open fun wrap (s: WRAPPING, t: WRAPPING) {
        context.bindTexture(WebGLRenderingContext.TEXTURE_2D, self)
        context.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_WRAP_S, s.mode)
        context.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_WRAP_T, t.mode)
    }

    actual open fun image (w: Int, h: Int, data: ByteArray) =
        context.texImage2D(WebGLRenderingContext.TEXTURE_2D, 0, WebGLRenderingContext.RGBA, w, h, 0, WebGLRenderingContext.RGBA, WebGLRenderingContext.UNSIGNED_BYTE, Uint8Array(data.toTypedArray()))
}