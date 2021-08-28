package com.ekdorn.classicdungeon.shared.engine

import com.ekdorn.classicdungeon.shared.engine.utils.Image
import getPixels


internal actual object ResourceLoader {
    actual suspend fun loadImage(name: String): Image {
        // TODO: maybe use Game.scope?
        val png = getPixels("./$name")
        return Image(png.width, png.height, png.pixels)
    }
}
