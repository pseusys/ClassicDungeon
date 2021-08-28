package com.ekdorn.classicdungeon.shared.engine.generics

import com.ekdorn.classicdungeon.shared.engine.ResourceLoader
import com.ekdorn.classicdungeon.shared.gl.extensions.ImageTexture
import com.ekdorn.classicdungeon.shared.gl.extensions.Atlas


/**
 * This object contains single loaded textures and texture atlases.
 */
internal object TextureCache: Assigned {
    /**
     * This fallback string represents unknown name of texture.
     * It is used in case no texture is loaded or texture loaded incorrectly.
     * TODO: make an exception fallback to this method.
     */
    const val NO_TEXTURE = "notex"

    /**
     * Single resources dictionary, contains images and atlases with obscure frame order (e.g. chrome).
     */
    private val resources = mutableMapOf<String, ImageTexture>()
    /**
     * Atlases dictionary, contains atlases with obvious frame order like animations or items atlases.
     */
    private val atlases = mutableMapOf<String, Atlas<*>>()


    /**
     * Method returning resource image by associated string name.
     * @param resource string name of resource
     * @return ImageTexture resource
     */
    fun get (resource: String) = resources[resource]!!

    /**
     * Method returning resource atlas by associated string name.
     * @param resource string name of atlas
     * @return Atlas resource
     */
    @Suppress("UNCHECKED_CAST")
    fun <Key> getAtlas (resource: String) = atlases[resource]!! as Atlas<Key>


    /**
     * Method for asynchronous texture cache initialization.
     * It initializes both dictionaries with fallback images that should be GUARANTEED to exist.
     * It also loads splash screen image.
     * @param splash name of splash screen resource
     */
    suspend fun init (splash: String) {
        val noTexture = ResourceLoader.loadImage("$NO_TEXTURE.png")
        resources[NO_TEXTURE] = ImageTexture(noTexture)
        atlases[NO_TEXTURE] = Atlas(noTexture, listOf(0), 1, 1)
        load(splash)
    }

    /**
     * Method for loading single resource asynchronously.
     * @param textures names of textures to load
     */
    suspend fun load (vararg textures: String) = textures.forEach {
        resources[it] = ImageTexture(ResourceLoader.loadImage("$it.png"))
    }

    /**
     * Method for loading resource atlas asynchronously.
     * @param texture name of atlas to load
     * @param frames list of keys to atlas frames
     * @param width horizontal size of atlas
     * @param height vertical size of atlas
     */
    suspend fun <Key> loadAtlas (texture: String, frames: List<Key>, width: Int = 1, height: Int = 1) {
        atlases[texture] = Atlas(ResourceLoader.loadImage("$texture.png"), frames, width, height)
    }


    /**
     * Method triggered on game ended, freeing resources and atlases.
     */
    override fun gameEnded () {
        resources.forEach { it.value.delete() }
        atlases.forEach { it.value.delete() }
    }
}
