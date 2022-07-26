package com.ekdorn.classicdungeon.shared.engine.general

import com.ekdorn.classicdungeon.shared.gl.primitives.Image as RawImage
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.dom.Audio
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image
import kotlin.js.Promise


internal actual object ResourceLoader {
    actual suspend fun loadImage(name: String) = Promise<RawImage> { resolve, reject ->
        val path = "./$name"
        Image().apply {
            onload = {
                val canvas = document.createElement("canvas") as HTMLCanvasElement
                canvas.width = width
                canvas.height = height

                val context = canvas.getContext("2d") as CanvasRenderingContext2D
                context.drawImage(this, 0.0, 0.0)

                val pixels = context.getImageData(0.0, 0.0, width.toDouble(), height.toDouble())
                resolve(RawImage(width, height, pixels.data.unsafeCast<ByteArray>()))
            }
            onerror = { _, _, _, _, _ -> reject(ResourceNotFoundException(path)) }
            src = path
        }
    }.await()

    actual suspend fun loadDataString (name: String) = window.fetch("./$name").await().text().await()

    actual suspend fun loadSound (name: String): Any = Promise { resolve, reject ->
        val path = "./$name"
        Audio(path).apply {
            onerror = { _, _, _, _, _ -> reject(ResourceNotFoundException(path)) }
            oncanplaythrough = { resolve(this) }
        }
    }.await()
}
