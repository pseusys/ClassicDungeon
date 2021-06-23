package com.ekdorn.classicdungeon.shared.glwrapper

import com.ekdorn.classicdungeon.shared.dependant.GLFunctions
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.utils.Image

internal class ImageTexture private constructor (private var image: Image, filtering: GLFunctions.Texture.FILTERING_MODE, wrapping: GLFunctions.Texture.WRAPPING_MODE): Texture() {
    constructor (image: Image): this(image, GLFunctions.Texture.FILTERING_MODE.LINEAR, GLFunctions.Texture.WRAPPING_MODE.NEAREST)

    private var filteringMin: GLFunctions.Texture.FILTERING_MODE
    private var filteringMag: GLFunctions.Texture.FILTERING_MODE
    private var wrappingS: GLFunctions.Texture.WRAPPING_MODE
    private var wrappingT: GLFunctions.Texture.WRAPPING_MODE
    init {
        fill(image)
        filteringMin = filtering
        filteringMag = filtering
        filter(filteringMin, filteringMag)
        wrappingS = wrapping
        wrappingT = wrapping
        wrap(wrappingS, wrappingT)
    }


    inline fun width (): Int = image.width
    inline fun height (): Int = image.height


    override fun filter (min: GLFunctions.Texture.FILTERING_MODE, mag: GLFunctions.Texture.FILTERING_MODE) {
        filteringMin = min
        filteringMag = mag
        super.filter(min, mag)
    }

    override fun wrap (s: GLFunctions.Texture.WRAPPING_MODE, t: GLFunctions.Texture.WRAPPING_MODE) {
        wrappingS = s
        wrappingT = t
        super.wrap(s, t)
    }

    fun fill (image: Image): Unit = fill(image.width, image.height, image.pixels)

    fun reload () {
        delete()
        createId()
        filter(filteringMin, filteringMag)
        wrap(wrappingS, wrappingT)
    }


    fun framePercent (left: Double, top: Double, right: Double, bottom: Double): Rectangle =
        Rectangle(left / image.width, top / image.height, right / image.width, bottom / image.height)
}