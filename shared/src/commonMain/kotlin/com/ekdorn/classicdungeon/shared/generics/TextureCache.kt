package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.dependant.ResourceLoader
import com.ekdorn.classicdungeon.shared.glextensions.ImageTexture
import com.ekdorn.classicdungeon.shared.glextensions.Atlas

internal object TextureCache: Assigned {
    const val NO_TEXTURE = "notex"

    private val resources = mutableMapOf<String, ImageTexture>()

    fun get (resource: String) = resources[resource]!!

    suspend fun load (vararg textures: String) = textures.asIterable().plus(NO_TEXTURE).forEach {
        resources[it] = ImageTexture(ResourceLoader.loadImage("$it.png"))
    }

    suspend fun <Key> loadAtlas (texture: String, frames: List<Key>, width: Int = 1, height: Int = 1) {
        resources[texture] = Atlas(ResourceLoader.loadImage("$texture.png"), frames, width, height)
    }

    override fun gameEnded () = resources.forEach { it.value.delete() }
}
