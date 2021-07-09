package com.ekdorn.classicdungeon.shared.dependant

import com.ekdorn.classicdungeon.shared.utils.Image
import getPixels

internal actual object ResourceLoader {
    actual suspend fun loadImage(name: String): Image {
        val png = getPixels("./$name")
        return Image(png.width, png.height, png.pixels)
    }
}
