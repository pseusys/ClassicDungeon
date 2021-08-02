package com.ekdorn.classicdungeon.shared.dependant.gl

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions.context


actual class GLUniform actual constructor (program: GLProgram, name: String) {
    private val self = context.getUniformLocation(program.self, name)


    actual fun value1i (value: Int) = context.uniform1i(self, value)

    actual fun value4f (value1: Float, value2: Float, value3: Float, value4: Float) = context.uniform4f(self, value1, value2, value3, value4)

    actual fun value4m(value: FloatArray) = context.uniformMatrix4fv(self, false, value.toTypedArray())
}