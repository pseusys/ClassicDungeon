package com.ekdorn.classicdungeon.shared.gl.wrapper


/**
 * GLShader class represents a GL shader of any type.
 *
 * [OpenGL wiki entry](https://www.khronos.org/opengl/wiki/Shader)
 * @param type shader type
 */
expect class GLShader (type: TYPE) {
    /**
     * Types of the shader:
     * - VERTEX: used to draw vertexes.
     * - FRAGMENT: used to fill space between vertexes.
     */
    enum class TYPE {
        VERTEX, FRAGMENT
    }

    /**
     * Function, preparing this shader to be linked.
     * Includes setting source code and compilation.
     * @param code shader source code
     * @return string, representing compilation error error; null if compilation successful
     */
    fun prepare (code: String): String?

    /**
     * Function, deleting the shader.
     * Should be called after program, the shader attached to, is linked.
     * @see GLProgram.link linking program
     */
    fun delete ()
}
