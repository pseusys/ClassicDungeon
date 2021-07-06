package com.ekdorn.classicdungeon.shared.dependant.gl

/**
 * GLBuffer class represents a GL buffer, a vertex buffer object in particular.
 * A buffer is associated with each UI element. Should be created along with each element.
 *
 * [OpenGL wiki entry](https://www.khronos.org/opengl/wiki/Vertex_Specification#Vertex_Buffer_Object)
 * @param size initial size of the VBO in bytes
 * @see com.ekdorn.classicdungeon.shared.ui.ElementUI UI element
 */
expect class GLBuffer (size: Int) {
    /**
     * Buffer size
     */
    val size: Int

    /**
     * Bind this buffer.
     * Should be called before draw event.
     * @see GLFunctions.drawElements draw event
     */
    fun bind ()

    /**
     * Fill this buffer with data.
     * Should be called once associated UI element has changed.
     * @param data array to fill data from
     */
    fun fill (data: FloatArray)

    /**
     * Release this buffer.
     * Should be called after draw event.
     * @see GLFunctions.drawElements draw event
     */
    fun release ()

    /**
     * Delete this buffer.
     * Should be called once associated UI element is about to be deleted.
     */
    fun delete ()
}