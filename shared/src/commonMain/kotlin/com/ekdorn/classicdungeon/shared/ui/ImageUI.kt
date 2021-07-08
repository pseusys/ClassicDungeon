package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.glextensions.ImageTexture
import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector


// 8 = 2 coords per vertex, 4 vertexes
internal class ImageUI private constructor (rect: Rectangle): ElementUI(rect, 8) {
    private companion object ImageDelay {
        val delay = Rectangle(0F, 0F, 1F, -1F).toPointsArray()
    }

    constructor (resource: String, frame: Rectangle, pos: Vector, width: Float = -1F, height: Float = -1F): this(Rectangle(pos.x, pos.y, width, height)) {
        texture(resource)
        if ((width == -1F) && (height == -1F)) metrics.apply { x = 1F; y = 1F }
        else {
            floatingWidth = width == -1F
            floatingHeight = height == -1F
        }
        frame(frame)
    }

    constructor (resource: String, pos: Vector, width: Float = -1F, height: Float = -1F): this(resource, Rectangle(0F, 1F, 1F, 0F), pos, width, height)


    var floatingWidth = false
    var floatingHeight = false
    override var parent: LayoutUI? = null
        set (value) {
            if (value != null) parentalResize(value.pixelMetrics.w, value.pixelMetrics.h)
            else {
                if (floatingWidth) metrics.x = -1F
                if (floatingHeight) metrics.y = -1F
            }
            field = value
        }

    fun parentalResize (pixelWidth: Int, pixelHeight: Int) {
        if (floatingWidth) metrics.x = (pixelHeight * metrics.y * texture.width()) / (texture.height() * pixelWidth)
        if (floatingHeight) metrics.y = (pixelWidth * metrics.x * texture.height()) / (texture.width() * pixelHeight)
        updateVertices()
    }


    private lateinit var texture: ImageTexture
    private lateinit var frame: Rectangle
    private lateinit var textureVertices: Rectangle

    var mirroredH: Boolean = false
        set (v) {
            field = v
            updateVertices()
        }
    var mirroredV: Boolean = false
        set (v) {
            field = v
            updateVertices()
        }


    override fun clone () = ImageUI(Rectangle(coords.x, coords.y, metrics.x, metrics.y)).also {
        it.floatingHeight = floatingHeight
        it.floatingWidth = floatingWidth
        it.texture = texture
        it.frame = frame
    }


    // TODO: combine with updateVertices if needed!
    fun texture (resource: String) {
        texture = TextureCache.get(resource)
    }

    fun frame (rect: Rectangle) {
        frame = rect
        //width = 1.0 //rect.width() * texture.width() / 1000
        //height = 1.0 //rect.height() * texture.height() / 1000
    }


    private fun updateVertices () {
        val x = if (mirroredH) Pair(frame.right, frame.left)
        else Pair(frame.left, frame.right)

        val y = if (mirroredV) Pair(frame.bottom, frame.top)
        else Pair(frame.top, frame.bottom)

        textureVertices = Rectangle(x.first, y.first, x.second, y.second)
        updateBuffer(2, delay, textureVertices.toPointsArray())
    }

    override fun draw () {
        super.draw()
        texture.bind()
        Script.setTexture(texture)
        Script.drawSingle()
        texture.release()
    }
}
