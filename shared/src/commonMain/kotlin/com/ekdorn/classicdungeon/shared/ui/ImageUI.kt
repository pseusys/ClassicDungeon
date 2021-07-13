package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.glextensions.ImageTexture
import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector


// 8 = 2 coords per vertex, 4 vertexes
internal open class ImageUI private constructor (pos: Vector, width: Float, height: Float): ElementUI(pos, width, height) {
    private companion object ImageDelay {
        val delay = Rectangle(0F, 0F, 1F, -1F).toPointsArray()
    }

    constructor (resource: String, frame: Rectangle, pos: Vector, width: Float = -1F, height: Float = -1F): this(pos, width, height) {
        texture(resource)
        frame(frame)
        updateVertices()
    }

    constructor (resource: String, pos: Vector, width: Float = -1F, height: Float = -1F): this(resource, Rectangle(0F, 1F, 1F, 0F), pos, width, height)


    override fun resize (ratio: Float) {
        if (preserving) {
            if (idealWidth == -1F) metrics.x = (idealWidth * texture.image.ratio * frame.ratio) / ratio
            else if (idealHeight == -1F) metrics.y = (idealHeight * ratio) / (texture.image.ratio * frame.ratio)
            else {
                val newWidth = (idealHeight * texture.image.ratio * frame.ratio) / ratio
                val newHeight = (idealWidth * ratio) / (texture.image.ratio * frame.ratio)
                if (newWidth <= idealWidth) {
                    metrics.x = newWidth
                    metrics.y = idealHeight
                } else {
                    metrics.x = idealWidth
                    metrics.y = newHeight
                }
            }
        } else {
            if (idealWidth == -1F) metrics.x = 1F - coords.x
            if (idealHeight == -1F) metrics.y = 1F - coords.y
        }
    }


    protected lateinit var texture: ImageTexture
    private lateinit var frame: Rectangle

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


    // TODO: remove if not needed!!
    fun texture (resource: String) {
        texture = TextureCache.get(resource)
    }

    fun frame (rect: Rectangle) {
        frame = rect
        updateVertices()
    }


    final override fun updateVertices () {
        val x = if (mirroredH) Pair(frame.right, frame.left)
        else Pair(frame.left, frame.right)

        val y = if (mirroredV) Pair(frame.bottom, frame.top)
        else Pair(frame.top, frame.bottom)

        val textureVertices = Rectangle(x.first, y.first, x.second, y.second)
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
