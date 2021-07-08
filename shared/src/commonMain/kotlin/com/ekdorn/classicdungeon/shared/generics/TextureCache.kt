package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.dependant.ResourceLoader
import com.ekdorn.classicdungeon.shared.glextensions.ImageTexture

internal object TextureCache: Assigned {
    private val resources = mutableMapOf<String, ImageTexture>()

    fun get (resource: String) = resources[resource]!!

    suspend fun load (vararg textures: String) = textures.forEach {
        resources[it] = ImageTexture(ResourceLoader.loadImage("$it.png"))
    }

    override fun gameEnded () = resources.forEach { it.value.delete() }
}
