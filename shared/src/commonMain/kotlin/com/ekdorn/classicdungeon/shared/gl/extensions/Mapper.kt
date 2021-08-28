package com.ekdorn.classicdungeon.shared.gl.extensions

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLBuffer
import com.ekdorn.classicdungeon.shared.engine.generics.Assigned


/**
 * Mapper object takes in account *indices* array for GLDrawElements calls.
 */
object Mapper: Assigned {
    /**
     * Array, representing order of vertices to draw. In this case (GL_TRIANGLES):
     * ┌───────┐
     * │ 0 ─ 1 │
     * │ │ / │ │
     * │ 3 ─ 2 │
     * └───────┘
     */
    private val INDICES = shortArrayOf(0, 1, 3, 2, 3, 1)

    /**
     * Actual pointer to indices array.
     * This array is as long as longest frame sequence ever drawn with a single call multiplied by INDICES length.
     * It may be longer than needed for current GLDrawElements call, GL will ignore extra elements.
     */
    private val POINTER = INDICES.copyOf().toMutableList()

    /**
     * GL buffer, containing indices.
     */
    val buffer = GLBuffer(GLBuffer.TYPE.ELEMENT)

    init { buffer.fillStatic(POINTER.toShortArray()) }

    /**
     * Function for predicting indices array length.
     * @param textures number of textures
     * @return predicted length of indices array for textures
     */
    fun elementsForTextures (textures: Int) = INDICES.size * textures

    /**
     * Function for adjusting indices array to new amount of textures.
     * @param textures number of textures
     */
    fun requestFor (textures: Int) {
        val currentTextures = POINTER.size / INDICES.size
        if (currentTextures >= textures) return
        for (tex in currentTextures until textures) POINTER.addAll(INDICES.map { (it + tex * 4).toShort() })
        buffer.fillStatic(POINTER.toShortArray())
    }

    /**
     * Function, clearing buffer on game finished.
     */
    override fun gameEnded() = buffer.delete()
}
