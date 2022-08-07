package com.ekdorn.classicdungeon.shared.engine.cache

import com.ekdorn.classicdungeon.shared.gl.primitives.Image
import com.ekdorn.classicdungeon.shared.libraries.getPixels
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.dom.Audio
import kotlin.js.Promise


// TODO: setup cache with https://www.npmjs.com/package/recurve-cache
internal actual object ResourceLoader {
    actual suspend fun loadImage(name: String): Image {
        val png = getPixels("./$name")
        return Image(png.width, png.height, png.pixels)
    }

    actual suspend fun loadDataString (name: String) = window.fetch("./$name").await().text().await()

    actual suspend fun loadSound (name: String): Any = Promise { resolve, reject ->
        val path = "./$name"
        Audio(path).apply {
            onerror = { _, _, _, _, _ -> reject(ResourceNotFoundException(path)) }
            oncanplaythrough = { resolve(this) }
        }
    }.await()
}
