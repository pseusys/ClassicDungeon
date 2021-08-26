package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.generics.TextureCache
import com.ekdorn.classicdungeon.shared.engine.glextensions.Script
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle
import com.ekdorn.classicdungeon.shared.engine.maths.Vector


/**
 * ImageUI - image, that can not be resized for it pixel sizes not to be disturbed.
 */
internal open class ImageUI (initializer: Map<String, *> = hashMapOf<String, Any>()): WidgetUI(initializer) {
    private companion object { val delay = Rectangle(0F, 0F, 1F, -1F).toPointsArray() }



    /**
     * Part of parent widget this widget takes.
     */
    @Implicit override var dimens = super.dimens
        get () = if (parent != null) metrics / parentMetrics()!! else field

    /**
     * Property texture - image source.
     * Fallback image by default.
     */
    var texture = TextureCache.get(initializer.getOrElse("resource") { TextureCache.NO_TEXTURE } as String)
        set (v) {
            metrics = v.image.metrics * frame.metrics * pixelation
            field = v
        }

    /**
     * Property frame - which part of image source is displayed.
     * Whole image by default.
     */
    var frame = initializer.getOrElse("frame") { Rectangle(0F, 1F, 1F, 0F) } as Rectangle
        set (v) {
            metrics = texture.image.metrics * v.metrics * pixelation
            dirty = true
            field = v
        }

    /**
     * Property mirroredH - whether this image should be mirrored horizontally.
     * False by default.
     */
    var mirroredH = initializer.getOrElse("mirroredH") { false } as Boolean
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Property mirroredV - whether this image should be mirrored vertically.
     * False by default.
     */
    var mirroredV = initializer.getOrElse("mirroredV") { false } as Boolean
        set (v) {
            dirty = true
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
        updateBuffer(delay, textureVertices.toPointsArray())
    }
}
