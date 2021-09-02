package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.cache.Gallery
import com.ekdorn.classicdungeon.shared.gl.extensions.Script
import com.ekdorn.classicdungeon.shared.engine.atomic.Rectangle
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector


/**
 * FrameUI - resizable background for UI elements.
 * It has four image corners, four one-dimension stretching borders and one two-dimension stretching filler.
 * ┌──────────────────────┐
 * │ corner border corner │
 * │ border filler border │
 * │ corner border corner │
 * └──────────────────────┘
 * All parts but upper left corner are optional. Following configurations are possible:
 * TODO: make them possible.
 * ┌──────────────────────┐
 * │ corner border corner │
 * └──────────────────────┘
 * ┌────────┐
 * │ corner │
 * │ border │
 * │ corner │
 * └────────┘
 * ┌────────┐
 * │ corner │
 * └────────┘
 */
internal class FrameUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ResizableUI(initializer) {
    /**
     * Property stretchW - whether widget can be stretched horizontally.
     * Enables horizontal border(s) and right corner(s).
     * True be default.
     */
    public override var stretchW = super.stretchW
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Property stretchW - whether widget can be stretched vertically.
     * Enables vertical border(s) and lower corner(s).
     * True be default.
     */
    public override var stretchH = super.stretchH
        set (v) {
            dirty = true
            field = v
        }


    /**
     * Property frame - which part of image source is mapped.
     * Measured from lower left corner.
     * Whole image by default.
     */
    var frame = Rectangle.create(initializer["frame"] as String?, Rectangle())
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Inline property for setting frame in pixels.
     */
    inline var pixelFrame: Rectangle
        get () = frame * texture.image.metrics
        set (v) {
            frame = v / texture.image.metrics
        }

    /**
     * Property texture - image source to map.
     * Fallback image by default.
     */
    var texture = Gallery.get(initializer.getOrElse("texture") { Gallery.DEFAULT } as String)

    /**
     * Property border - part of image to map as border, vertical and horizontal.
     * Both zero by default.
     */
    var border = Vector.create(initializer["border"] as String?, Vector())
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Inline property for setting border in pixels.
     */
    inline var pixelBorder: Vector
        get () = border * pixelFrame.metrics
        set (v) {
            border = v / pixelFrame.metrics
        }


    init {
        if ("pixelFrame" in initializer) pixelFrame = Rectangle.create(initializer["pixelFrame"] as String?, Rectangle())
        if ("pixelBorder" in initializer) pixelBorder = Vector.create(initializer["pixelBorder"] as String?, Vector())
        updateVertices()
    }



    override fun draw () {
        super.draw()
        texture.bind()
        Script.setTexture(texture)
        Script.drawMultiple(9)
        texture.release()
    }



    override fun updateVertices () {
        super.updateVertices()
        val pxlBorder = pixelBorder * pixelation / metrics
        val vertices = listOf(
            Rectangle(0F, 0F, pxlBorder.x, -pxlBorder.y),
            Rectangle(pxlBorder.x, 0F, 1 - pxlBorder.x, -pxlBorder.y),
            Rectangle(1 - pxlBorder.x, 0F, 1F, -pxlBorder.y),

            Rectangle(0F, -pxlBorder.y, pxlBorder.x, pxlBorder.y - 1),
            Rectangle(pxlBorder.x, -pxlBorder.y, 1 - pxlBorder.x, pxlBorder.y - 1),
            Rectangle(1 - pxlBorder.x, -pxlBorder.y, 1F, pxlBorder.y - 1),

            Rectangle(0F, pxlBorder.y - 1, pxlBorder.x, -1F),
            Rectangle(pxlBorder.x, pxlBorder.y - 1, 1 - pxlBorder.x, -1F),
            Rectangle(1 - pxlBorder.x, pxlBorder.y - 1, 1F, -1F),
        )

        val bord = border * frame.metrics
        val textures = listOf(
            Rectangle(frame.left, frame.top, frame.left + bord.x, frame.top - bord.y),
            Rectangle(frame.left + bord.x, frame.top, frame.right - bord.x, frame.top - bord.y),
            Rectangle(frame.right - bord.x, frame.top, frame.right, frame.top - bord.y),

            Rectangle(frame.left, frame.top - bord.y, frame.left + bord.x, frame.bottom + bord.y),
            Rectangle(frame.left + bord.x, frame.top - bord.y, frame.right - bord.x, frame.bottom + bord.y),
            Rectangle(frame.right - bord.x, frame.top - bord.y, frame.right, frame.bottom + bord.y),

            Rectangle(frame.left, frame.bottom + bord.y, frame.left + bord.x, frame.bottom),
            Rectangle(frame.left + bord.x, frame.bottom + bord.y, frame.right - bord.x, frame.bottom),
            Rectangle(frame.right - bord.x, frame.bottom + bord.y, frame.right, frame.bottom)
        )

        buffer.fill(vertices, textures)
    }
}
