package com.ekdorn.classicdungeon.shared.gl.wrapper


/**
 * GLUniform class represents a GL uniform.
 * It needs a GL program with shaders attached and a string name, used in shaders, to be initialized.
 *
 * [OpenGL wiki entry](https://www.khronos.org/opengl/wiki/Uniform_(GLSL))
 * @param program program that includes this value
 * @param name name of this value in shader
 * @see GLProgram program
 * @see GLShader shader
 * @see GLProgram.attach attach shader to program
 */
expect class GLUniform (program: GLProgram, name: String) {
    /**
     * Function, setting 1 integer value to the uniform.
     * @param value integer to be set
     */
    fun value1i (value: Int)

    /**
     * Function, setting 4 float values to the uniform (a vec4).
     * @param array vec4 array
     */
    fun value4fv (array: FloatArray)

    /**
     * Function, setting 4x4 matrix to the uniform (a mat4).
     * @param value matrix, represented as array
     */
    fun value4m (value: FloatArray)
}