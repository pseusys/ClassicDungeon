package com.ekdorn.classicdungeon.shared.engine

import com.ekdorn.classicdungeon.shared.engine.utils.Image
import kotlinx.browser.document
import kotlinx.coroutines.await
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.js.Promise
import org.w3c.dom.Image as DOMImage


internal actual object ResourceLoader {
    actual suspend fun loadImage(name: String) = Promise<Image> { resolve, reject ->
        val path = "./$name"
        val img = DOMImage()
        img.crossOrigin = "Anonymous"
        img.onload = {
            val canvas = document.createElement("canvas") as HTMLCanvasElement
            canvas.width = img.width
            canvas.height = img.height

            val context = canvas.getContext("2d") as CanvasRenderingContext2D
            context.drawImage(img, 0.0, 0.0)

            val pixels = context.getImageData(0.0, 0.0, img.width.toDouble(), img.height.toDouble())
            resolve(Image(img.width, img.height, pixels.data.unsafeCast<ByteArray>()))
        }
        img.onerror = { _, _, _, _, _ -> reject(ResourceNotFoundException(path)) }
        img.src = path
    }.await()
}
