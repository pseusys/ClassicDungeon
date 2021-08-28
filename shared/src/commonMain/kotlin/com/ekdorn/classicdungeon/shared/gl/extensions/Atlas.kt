package com.ekdorn.classicdungeon.shared.gl.extensions

import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle
import com.ekdorn.classicdungeon.shared.engine.utils.Image


/**
 * Class, representing atlas - an image divided in equal parts in obvious way.
 * It cuts this images, associating keys of different types with each of them.
 */
internal class Atlas <Key> (image: Image, keys: List<Key>, width: Int = 1, height: Int = 1): ImageTexture(image), MutableMap<Key, Rectangle> by HashMap(keys.size) {
    init {
        val images = width * height
        if (images != keys.size) throw Exception("ImageMap size ($images) != keys collection size (${keys.size})")
        for (row in 0 until height) for (column in 0 until width)
            put(keys[row * width + column], Rectangle(column / width.toFloat(), (row + 1) / height.toFloat(), (column + 1) / width.toFloat(), row / height.toFloat()))
    }
}
