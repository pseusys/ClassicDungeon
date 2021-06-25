package com.ekdorn.classicdungeon.shared.dependant

import com.ekdorn.classicdungeon.shared.utils.Image

expect object ResourceLoader {
    suspend fun loadImage (name: String): Image
}
