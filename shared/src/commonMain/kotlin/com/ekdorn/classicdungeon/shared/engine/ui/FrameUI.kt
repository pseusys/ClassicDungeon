package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.cache.Image
import com.ekdorn.classicdungeon.shared.gl.extensions.Script
import com.ekdorn.classicdungeon.shared.engine.atomic.Rectangle
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


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
@Serializable
@SerialName("FrameUI")
internal class FrameUI: ResizableUI() {
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

    var source = Image.DEFAULT
        set (v) {
            field = v
            texture = Image.get(field)
        }

    /**
     * Inline property for setting frame in pixels.
     */
    var pixelFrame = Rectangle()
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Inline property for setting border in pixels.
     */
    var pixelBorder = Vector()
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Property border - part of image to map as border, vertical and horizontal.
     * Both zero by default.
     */
    inline var border
        get() = pixelBorder / pixelFrame.metrics
        set (v) {
            pixelBorder = v * pixelFrame.metrics
        }

    /**
     * Property frame - which part of image source is mapped.
     * Measured from lower left corner.
     * Whole image by default.
     */
    inline var frame
        get() = pixelFrame / texture.image.metrics
        set (v) {
            pixelFrame = v * texture.image.metrics
        }


    /**
     * Property texture - image source to map.
     * Fallback image by default.
     */
    @Transient private var texture = Image.get(source)


    init { updateVertices() }


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
