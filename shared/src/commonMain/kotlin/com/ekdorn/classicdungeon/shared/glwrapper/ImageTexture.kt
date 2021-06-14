package com.ekdorn.classicdungeon.shared.glwrapper

import com.ekdorn.classicdungeon.shared.dependant.GLFunctions
import com.ekdorn.classicdungeon.shared.utils.Image

internal class ImageTexture private constructor (): Texture() {
    private lateinit var img: Image

    constructor (image: Image, filtering: GLFunctions.FILTERING_MODE, wrapping: GLFunctions.WRAPPING_MODE): this() {
        img = image
        fill(img)
        filter(filtering, filtering)
        wrap(wrapping, wrapping)
    }

    constructor (image: Image): this(image, GLFunctions.FILTERING_MODE.LINEAR, GLFunctions.WRAPPING_MODE.NEAREST)


    fun fill (image: Image): Unit = fill(image.width, image.height, image.pixels)
}