package com.ekdorn.classicdungeon.shared.dependant

import com.ekdorn.classicdungeon.shared.utils.Image
import getPixels

actual object ResourceLoader {
    actual suspend fun loadImage(name: String): Image {
        val png = getPixels("./sample.png")
        return Image(png.width, png.height, png.pixels)
    }
}
