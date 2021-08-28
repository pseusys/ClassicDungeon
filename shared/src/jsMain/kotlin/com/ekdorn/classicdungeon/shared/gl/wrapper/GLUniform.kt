package com.ekdorn.classicdungeon.shared.gl.wrapper

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions.context


actual class GLUniform actual constructor (program: GLProgram, name: String) {
    private val self = context.getUniformLocation(program.self, name)


    actual fun value1i (value: Int) = context.uniform1i(self, value)

    actual fun value4fv (array: FloatArray) = context.uniform4fv(self, array.toTypedArray())

    actual fun value4m(value: FloatArray) = context.uniformMatrix4fv(self, false, value.toTypedArray())
}