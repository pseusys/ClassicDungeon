package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.cache.Image
import com.ekdorn.classicdungeon.shared.gl.extensions.Script
import com.ekdorn.classicdungeon.shared.engine.atomic.Rectangle
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


/**
 * ImageUI - image, that can not be resized for it pixel sizes not to be disturbed.
 */
@Serializable
@SerialName("ImageUI")
internal open class ImageUI: WidgetUI() {
    var source = Image.DEFAULT
        set (v) {
            field = v
            texture = Image.get(field)
        }

    /**
     * Property frame - which part of image source is displayed.
     * Measured from lower left corner.
     * Whole image by default.
     */
    var frame = Rectangle()
        set (v) {
            metrics = texture.image.metrics * v.metrics * pixelation
            dirty = true
            field = v
        }

    /**
     * Property mirroredH - whether this image should be mirrored horizontally.
     * False by default.
     */
    var mirroredH = false
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Property mirroredV - whether this image should be mirrored vertically.
     * False by default.
     */
    var mirroredV = false
        set (v) {
            dirty = true
            field = v
        }


    /**
     * Part of parent widget this widget takes.
     */
    @Transient override var dimens = super.dimens
        get () = if (parent != null) metrics / parentMetrics()!! else field

    /**
     * Property texture - image source.
     * Fallback image by default.
     */
    @Transient protected open var texture = Image.get(source)
        set (v) {
            metrics = v.image.metrics * frame.metrics * pixelation
            field = v
        }


    init { updateVertices() }


    override fun draw () {
        super.draw()
        texture.bind()
        Script.setTexture(texture)
        Script.drawSingle()
        texture.release()
    }


    override fun translate(parentCoords: Vector, parentMetrics: Vector) {
        metrics = texture.image.metrics * frame.metrics * pixelation
        super.translate(parentCoords, parentMetrics)
    }

    final override fun updateVertices () {
        super.updateVertices()

        val x = if (mirroredH) Pair(frame.right, frame.left)
        else Pair(frame.left, frame.right)

        val y = if (mirroredV) Pair(frame.bottom, frame.top)
        else Pair(frame.top, frame.bottom)

        val textureVertices = Rectangle(x.first, y.first, x.second, y.second)
        buffer.fill(Rectangle(0F, 0F, 1F, -1F), textureVertices)
    }
}
