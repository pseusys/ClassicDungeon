package com.ekdorn.classicdungeon.shared.gl.extensions

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLTexture
import com.ekdorn.classicdungeon.shared.engine.atomic.Rectangle
import com.ekdorn.classicdungeon.shared.gl.primitives.Image


/**
 * Image texture - a wrapper for GLTexture, bundling information about filtering, wrapping and source image.
 * Default constructor allows setting both filtering and wrapping.
 */
internal open class ImageTexture (img: Image, filtering: FILTERING, wrapping: WRAPPING): GLTexture() {
    constructor (image: Image): this(image, FILTERING.NEAREST, WRAPPING.CLAMP)

    /**
     * Minification and magnification filters.
     */
    private var filteringMin: FILTERING
    private var filteringMag: FILTERING

    /**
     * S and T coordinate wrapping.
     */
    private var wrappingS: WRAPPING
    private var wrappingT: WRAPPING

    /**
     * Source image itself.
     */
    var image = img
        set (v) {
            image(v.metrics.w, v.metrics.h, v.pixels)
            field = v
        }

    init {
        filteringMin = filtering
        filteringMag = filtering

        wrappingS = wrapping
        wrappingT = wrapping

        image = img
    }


    final override fun image (w: Int, h: Int, data: ByteArray) {
        super.image(w, h, data)
        filter(filteringMin, filteringMag)
        wrap(wrappingS, wrappingT)
    }

    /**
     * Filtering function - sets filtering.
     * @param minification minification filter
     * @param magnification magnification filter
     */
    final override fun filter (minification: FILTERING, magnification: FILTERING) {
        filteringMin = minification
        filteringMag = magnification
        super.filter(minification, magnification)
    }

    /**
     * Wrapping function - sets wrapping.
     * @param s s coordinate wrapping mode
     * @param t t coordinate wrapping mode
     */
    final override fun wrap (s: WRAPPING, t: WRAPPING) {
        wrappingS = s
        wrappingT = t
        super.wrap(s, t)
    }

    /**
     * Reloading function, allows resetting GLTexture.
     * TODO: check if needed?
     */
    fun reload () {
        delete()
        generate()
        filter(filteringMin, filteringMag)
        wrap(wrappingS, wrappingT)
    }


    /**
     * TODO: check if needed?
     */
    fun framePercent (left: Float, top: Float, right: Float, bottom: Float) =
        Rectangle(left / image.metrics.x, top / image.metrics.y, right / image.metrics.x, bottom / image.metrics.y)
}
