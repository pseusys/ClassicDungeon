package com.ekdorn.classicdungeon.shared.engine.cache

import com.ekdorn.classicdungeon.shared.engine.utils.Assigned
import com.ekdorn.classicdungeon.shared.gl.extensions.ImageTexture
import com.ekdorn.classicdungeon.shared.gl.extensions.Atlas


/**
 * This object contains single loaded textures and texture atlases.
 */
internal object Image: Assigned {
    /**
     * This fallback string represents unknown name of texture.
     * It is used in case no texture is loaded or texture loaded incorrectly.
     * TODO: make an exception fallback to this method.
     */
    const val DEFAULT = "notex"

    /**
     * Single resources' dictionary, contains images and atlases with obscure frame order (e.g. chrome).
     */
    private val textures = mutableMapOf<String, ImageTexture>()


    /**
     * Method returning resource image by associated string name.
     * @param resource string name of resource
     * @return ImageTexture resource
     */
    fun get (resource: String) = textures[resource]!!

    /**
     * Method returning resource atlas by associated string name.
     * @param resource string name of atlas
     * @return Atlas resource
     */
    @Suppress("UNCHECKED_CAST")
    fun <Key> getAtlas (resource: String) = textures[resource]!! as Atlas<Key>


    /**
     * Method for asynchronous texture cache initialization.
     * It initializes both dictionaries with fallback images that should be GUARANTEED to exist.
     * It also loads splash screen image.
     * @param splash name of splash screen resources
     */
    suspend fun init (splash: Array<String>) {
        loadAtlas(DEFAULT, listOf(0), 1, 1)
        load(*splash)
    }

    /**
     * Method for loading single resource asynchronously.
     * @param values names of textures to load
     */
    suspend fun load (vararg values: String) = values.forEach {
        textures[it] = ImageTexture(ResourceLoader.loadImage("images/$it.png"))
    }

    /**
     * Method for loading resource atlas asynchronously.
     * @param value name of atlas to load
     * @param frames list of keys to atlas frames
     * @param width horizontal size of atlas
     * @param height vertical size of atlas
     */
    suspend fun <Key> loadAtlas (value: String, frames: List<Key>, width: Int = 1, height: Int = 1) {
        textures[value] = Atlas(ResourceLoader.loadImage("images/$value.png"), frames, width, height)
    }


    /**
     * Method triggered on game ended, freeing resources and atlases.
     */
    override fun gameEnded () = textures.forEach { it.value.delete() }
}
