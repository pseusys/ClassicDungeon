package com.ekdorn.classicdungeon.shared.gl.wrapper

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions.context
import org.khronos.webgl.*


actual open class GLBuffer actual constructor (actual val type: TYPE) {
    actual enum class TYPE (val id: Int) {
        COMMON(WebGLRenderingContext.ARRAY_BUFFER), ELEMENT(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER)
    }

    private val self: WebGLBuffer? = context.createBuffer()

    actual fun bind () = context.bindBuffer(type.id, self)

    actual fun fillDynamic (data: FloatArray) {
        context.bindBuffer(type.id, self)
        context.bufferData(type.id, Float32Array(data.toTypedArray()), WebGLRenderingContext.DYNAMIC_DRAW)
    }

    actual fun fillStatic(data: ShortArray) {
        context.bindBuffer(type.id, self)
        context.bufferData(type.id, Int16Array(data.toTypedArray()), WebGLRenderingContext.STATIC_DRAW)
    }

    actual fun delete () = context.deleteBuffer(self)
}
