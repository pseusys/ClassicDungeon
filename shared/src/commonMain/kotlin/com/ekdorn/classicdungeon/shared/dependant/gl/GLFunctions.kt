package com.ekdorn.classicdungeon.shared.dependant.gl

/**
 * GLFunctions object is a collection of GL functions.
 */
expect object GLFunctions {
    /**
     * Function, initializing GL. Platform-specific. Includes setting fill color, texture loading flags, etc.
     * Should be called immediately after game started.
     */
    fun setup ()

    /**
     * Function, adjusting viewport size.
     * Should be called after GL surface dimensions changed.
     * @param width screen width
     * @param height screen height
     */
    fun viewport (width: Int, height: Int)

    // TODO: fix indices array.
    /**
     * Function drawing elements on screen.
     * It uses currently set uniform parameters and attribute parameters from currently bound VBO.
     * It also uses an indices array, that is to be fixed in future.
     * @param count count of vertexes to draw
     */
    fun drawElements (count: Int, indices: ByteArray)
}