package com.ekdorn.classicdungeon.shared.dependant.gl

import context
import org.khronos.webgl.WebGLRenderingContext

actual class GLShader actual constructor (type: TYPE) {
    actual enum class TYPE (val type: Int) {
        VERTEX(WebGLRenderingContext.VERTEX_SHADER), FRAGMENT(WebGLRenderingContext.FRAGMENT_SHADER)
    }


    val self = context.createShader(type.type)


    actual fun prepare (code: String): String? {
        context.shaderSource(self, code)
        context.compileShader(self)
        return if (context.getShaderParameter(self, WebGLRenderingContext.COMPILE_STATUS) != true) {
            context.getShaderInfoLog(self)
        } else null
    }

    actual fun delete () = context.deleteShader(self)
}
