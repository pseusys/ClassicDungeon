package com.ekdorn.classicdungeon.shared.dependant

import com.ekdorn.classicdungeon.shared.engine.utils.Image

internal expect object ResourceLoader {
    suspend fun loadImage (name: String): Image
}
