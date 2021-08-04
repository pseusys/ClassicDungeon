package com.ekdorn.classicdungeon.shared.dependant.gl


/**
 * GLAttribute class represents a GL attribute, a vertex array object in particular.
 * It needs a GL program with vertex shader attached and a string name, used in shader, to be initialized.
 *
 * [OpenGL wiki entry](https://www.khronos.org/opengl/wiki/Vertex_Specification#Vertex_Array_Object)
 * @param program program that includes this VAO
 * @param name name of this VAO in vertex shader
 * @see GLProgram program
 * @see GLShader shader
 * @see GLProgram.attach attach shader to program
 */
expect class GLAttribute (program: GLProgram, name: String) {
    /**
     * Function, enabling this VAO.
     * Should be called once the program is used.
     * @see GLProgram.use use program
     */
    fun enable ()

    /**
     * Function, disabling this VAO.
     * Should be called once the program is about to be deleted.
     * @see GLProgram.delete delete program
     */
    fun disable ()

    /**
     * Function, setting location of this VAO in currently bound buffer.
     * Should be called once per draw event.
     * @see GLBuffer.bind bind buffer
     * @see GLFunctions.drawElements draw event
     */
    fun set (size: Int, offset: Int, stride: Int)
}
