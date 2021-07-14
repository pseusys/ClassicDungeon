package com.ekdorn.classicdungeon.shared.engine.glextensions

import com.ekdorn.classicdungeon.shared.dependant.gl.GLBuffer
import com.ekdorn.classicdungeon.shared.engine.generics.Assigned

object Mapper: Assigned {
    private val INDICES = shortArrayOf(0, 1, 3, 2, 3, 1)
    private val POINTER = INDICES.copyOf().toMutableList()
    val buffer = GLBuffer(GLBuffer.TYPE.ELEMENT)

    init {
        buffer.fillStatic(POINTER.toShortArray())
    }

    fun elementsForTextures (textures: Int) = INDICES.size * textures

    fun requestFor (textures: Int) {
        val currentTextures = POINTER.size / INDICES.size
        if (currentTextures >= textures) return
        for (tex in currentTextures until textures) {
            POINTER.addAll(INDICES.map { (it + tex * 4).toShort() })
        }
        buffer.fillStatic(POINTER.toShortArray())
    }

    override fun gameEnded() = buffer.delete()
}
