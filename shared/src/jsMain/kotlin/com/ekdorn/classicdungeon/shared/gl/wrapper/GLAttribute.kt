package com.ekdorn.classicdungeon.shared.gl.wrapper

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions.context
import org.khronos.webgl.WebGLRenderingContext


actual class GLAttribute actual constructor (program: GLProgram, name: String) {
    private val self = context.getAttribLocation(program.self, name)


    actual fun enable () = context.enableVertexAttribArray(self)

    actual fun disable () = context.disableVertexAttribArray(self)

    actual fun set (size: Int, offset: Int, stride: Int) =
        context.vertexAttribPointer(self, size, WebGLRenderingContext.FLOAT, false, stride * Float.SIZE_BYTES, offset * Float.SIZE_BYTES)
}
