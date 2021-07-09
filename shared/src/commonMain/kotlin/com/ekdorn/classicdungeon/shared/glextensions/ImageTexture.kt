package com.ekdorn.classicdungeon.shared.glextensions

import com.ekdorn.classicdungeon.shared.dependant.gl.GLTexture
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.utils.Image

internal class ImageTexture private constructor (img: Image, filtering: FILTERING, wrapping: WRAPPING): GLTexture() {
    constructor (image: Image): this(image, FILTERING.NEAREST, WRAPPING.CLAMP)

    private var filteringMin: FILTERING
    private var filteringMag: FILTERING
    private var wrappingS: WRAPPING
    private var wrappingT: WRAPPING

    var image = img
        set (v) {
            image(v.width, v.height, v.pixels)
            field = v
        }

    init {
        image = img

        filteringMin = filtering
        filteringMag = filtering
        filter(filteringMin, filteringMag)

        wrappingS = wrapping
        wrappingT = wrapping
        wrap(wrappingS, wrappingT)
    }



    fun width () = image.width
    fun height () = image.height


    override fun filter (minification: FILTERING, magnification: FILTERING) {
        filteringMin = minification
        filteringMag = magnification
        super.filter(minification, magnification)
    }

    override fun wrap (s: WRAPPING, t: WRAPPING) {
        wrappingS = s
        wrappingT = t
        super.wrap(s, t)
    }

    fun reload () {
        delete()
        generate()
        filter(filteringMin, filteringMag)
        wrap(wrappingS, wrappingT)
    }


    fun framePercent (left: Float, top: Float, right: Float, bottom: Float) =
        Rectangle(left / image.width, top / image.height, right / image.width, bottom / image.height)
}