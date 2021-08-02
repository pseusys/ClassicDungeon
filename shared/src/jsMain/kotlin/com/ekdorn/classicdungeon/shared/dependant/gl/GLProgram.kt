package com.ekdorn.classicdungeon.shared.dependant.gl

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions.context
import org.khronos.webgl.WebGLRenderingContext


actual class GLProgram {
    val self = context.createProgram()


    actual fun use () = context.useProgram(self)

    actual fun delete () = context.deleteProgram(self)

    actual fun attach (shader: GLShader) = context.attachShader(self, shader.self)

    actual fun link (): String? {
        context.linkProgram(self)
        return if (context.getProgramParameter(self, WebGLRenderingContext.LINK_STATUS) != true) {
            context.getProgramInfoLog(self)
        } else null
    }
}
