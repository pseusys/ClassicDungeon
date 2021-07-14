package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.dependant.ResourceLoader
import com.ekdorn.classicdungeon.shared.glextensions.ImageTexture
import com.ekdorn.classicdungeon.shared.glextensions.Atlas

internal object TextureCache: Assigned {
    const val NO_TEXTURE = "notex"

    private val resources = mutableMapOf<String, ImageTexture>()
    private val atlases = mutableMapOf<String, Atlas<*>>()



    fun get (resource: String) = resources[resource]!!

    @Suppress("UNCHECKED_CAST")
    fun <Key> getAtlas (resource: String) = atlases[resource]!! as Atlas<Key>


    suspend fun init (splash: String) {
        val noTexture = ResourceLoader.loadImage("$NO_TEXTURE.png")
        resources[NO_TEXTURE] = ImageTexture(noTexture)
        atlases[NO_TEXTURE] = Atlas(noTexture, listOf(0), 1, 1)
        load(splash)
    }

    suspend fun load (vararg textures: String) = textures.forEach {
        resources[it] = ImageTexture(ResourceLoader.loadImage("$it.png"))
    }

    suspend fun <Key> loadAtlas (texture: String, frames: List<Key>, width: Int = 1, height: Int = 1) {
        atlases[texture] = Atlas(ResourceLoader.loadImage("$texture.png"), frames, width, height)
    }



    override fun gameEnded () = resources.forEach { it.value.delete() }
}
