package com.ekdorn.classicdungeon.shared.dependant

import com.ekdorn.classicdungeon.shared.engine.utils.Image

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
}
