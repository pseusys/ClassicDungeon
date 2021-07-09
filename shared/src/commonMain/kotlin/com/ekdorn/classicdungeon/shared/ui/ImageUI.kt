package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.glextensions.ImageTexture
import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector


// 8 = 2 coords per vertex, 4 vertexes
internal class ImageUI private constructor (pos: Vector, width: Float, height: Float): PreservingUI(pos, width, height) {
    private companion object ImageDelay {
        val delay = Rectangle(0F, 0F, 1F, -1F).toPointsArray()
    }

    constructor (resource: String, frame: Rectangle, pos: Vector, width: Float = -1F, height: Float = -1F): this(pos, width, height) {
        texture(resource)
        frame(frame)
        updateVertices()
    }

    constructor (resource: String, pos: Vector, width: Float = -1F, height: Float = -1F): this(resource, Rectangle(0F, 1F, 1F, 0F), pos, width, height)


    override fun parentalResize (pixelWidth: Int, pixelHeight: Int) {
        if (floatingWidth) metrics.x = (pixelHeight * metrics.y * texture.image.width) / (texture.image.height * pixelWidth)
        if (floatingHeight) metrics.y = (pixelWidth * metrics.x * texture.image.height) / (texture.image.width * pixelHeight)
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
