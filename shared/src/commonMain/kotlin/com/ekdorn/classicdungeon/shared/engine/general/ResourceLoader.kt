package com.ekdorn.classicdungeon.shared.engine.general

import com.ekdorn.classicdungeon.shared.audio.Sounds
import com.ekdorn.classicdungeon.shared.gl.primitives.Image


class ResourceNotFoundException (resource: String): Exception("Resource $resource not found!")

/**
 * Expect object for platform-specific resource loading.
 * The loaded resources are already not raw, but processed to simple data classes.
 */
internal expect object ResourceLoader {
    /**
     * This function loads an image from resources asynchronously by name.
     * The pixel order is not altered, it is done later by OpenGL means.
     * @param name string name of resource
     */
    suspend fun loadImage (name: String): Image

    suspend fun loadDataString (name: String): String

    suspend fun loadSound (name: String): Any
}


internal object ResourceLists {
    val splash_textures = arrayOf("notex")

    private val main_textures = arrayOf("")
    private val main_layouts = arrayOf("")
    private val main_atlases = arrayOf(
        arrayOf("bee", List(16) { it }, 16)
    )

    fun loadTextures() {

    }
}
