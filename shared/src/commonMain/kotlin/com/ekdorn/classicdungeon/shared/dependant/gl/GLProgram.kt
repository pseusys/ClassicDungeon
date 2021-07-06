package com.ekdorn.classicdungeon.shared.dependant.gl

/**
 * GLProgram class represents a GL program.
 * There is a single program for the game.
 *
 * [OpenGL wiki entry](https://www.khronos.org/opengl/wiki/GLSL_Object#Program_objects)
 */
expect class GLProgram () {
    /**
     * Function, using this program.
     * Should be called once program is prepared.
     */
    fun use ()

    /**
     * Function, deleting program.
     * Should be called in the end of app execution.
     */
    fun delete ()

    /**
     * Function, attaching a shader to the program.
     * @param shader shader to be attached
     */
    fun attach (shader: GLShader)

    /**
     * Function, linking the program.
     * Should be called once all shaders are attached to the program.
     * @return string, representing linking error; null if linking successful
     */
    fun link (): String?
}
