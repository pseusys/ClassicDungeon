package com.ekdorn.classicdungeon.shared.dependant.gl


/**
 * GLBuffer class represents a GL buffer, a vertex buffer object in particular.
 * A buffer is associated with each UI element. Should be created along with each element.
 *
 * [OpenGL wiki entry](https://www.khronos.org/opengl/wiki/Vertex_Specification#Vertex_Buffer_Object)
 * @param type buffer type
 * @see com.ekdorn.classicdungeon.shared.engine.ui.WidgetUI UI element
 */
expect class GLBuffer (type: TYPE) {
    val type: TYPE

    /**
     * Types of the buffer:
     * - COMMON: ARRAY_BUFFER, keeps vertex data.
     * - ELEMENT: ELEMENT_ARRAY_BUFFER, keeps vertex drawing data.
     */
    enum class TYPE {
        COMMON, ELEMENT
    }

    /**
     * Bind this buffer.
     * Should be called before draw event.
     * @see GLFunctions.drawElements draw event
     */
    fun bind ()

    /**
     * Fill this buffer with float data. Used for array buffers.
     * Should be called once associated UI element has changed.
     * @param data array to fill data from
     */
    fun fillDynamic (data: FloatArray)

    /**
     * Fill this buffer with int data. Used for element array buffers.
     * Should be called once associated UI element has changed.
     * @param data array to fill data from
     */
    fun fillStatic (data: ShortArray)

    /**
     * Delete this buffer.
     * Should be called once associated UI element is about to be deleted.
     */
    fun delete ()
}