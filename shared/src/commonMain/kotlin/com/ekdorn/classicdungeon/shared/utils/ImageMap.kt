package com.ekdorn.classicdungeon.shared.utils

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.glextensions.ImageTexture
import com.ekdorn.classicdungeon.shared.maths.Rectangle


internal open class ImageMap <Key> (texture: ImageTexture, keys: Array<Key>, val width: Int, val height: Int): MutableMap<Key, Rectangle> by HashMap() {
    constructor (texture: ImageTexture, keys: Array<Key>, width: Int): this(texture, keys, width, texture.image.width)
    constructor (texture: ImageTexture, keys: Array<Key>): this(texture, keys, texture.image.width, texture.image.height)
    constructor (resource: String, keys: Array<Key>, width: Int, height: Int): this(TextureCache.get(resource), keys, width, height)
    constructor (resource: String, keys: Array<Key>, width: Int): this(TextureCache.get(resource), keys, width)
    constructor (resource: String, keys: Array<Key>): this(TextureCache.get(resource), keys)

    init {
        val imagesX = texture.image.width / width
        val imagesY = texture.image.height / height
        val images = imagesX * imagesY
        if (images != keys.size) throw Exception("ImageMap size ($images) != keys collection size (${keys.size})")
        for (row in 0 until imagesX) for (column in 0 until imagesY)
            put(keys[column * imagesY + row], Rectangle(row * width, column * height, (row + 1) * width, (column + 1) * height))
    }
}
