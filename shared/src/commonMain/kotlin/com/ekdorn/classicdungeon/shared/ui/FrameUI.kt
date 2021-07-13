package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal class FrameUI (resource: String, rect: Rectangle, private val frame: Rectangle): LayoutUI(rect) {
    constructor (resource: String, rect: Rectangle): this(resource, rect, Rectangle(0F, 1F, 1F, 0F))
    constructor (resource: String, margin: Int): this(resource, Rectangle(margin, margin, 1 - 2 * margin, 1 - 2 * margin))

    private val image = TextureCache.get(resource)
    private var realFactor = Vector()

    var factor = Vector(0.1F, 0.1F)
    var border = 0.31818181818F

    init {
        updateVertices()
    }

    override fun resize (ratio: Float) {
        if (preserving) {
            if (factor.x == -1F) realFactor.x = factor.y * ratio
            else if (factor.y == -1F) realFactor.y = factor.x / ratio
            else {
                val factorWidth = factor.x / ratio
                val factorHeight = factor.y * ratio
                if (factorWidth <= factor.x) {
                    realFactor.x = factorWidth
                    realFactor.y = factor.y
                } else {
                    realFactor.x = factor.x
                    realFactor.y = factorHeight
                }
            }
        }
        updateVertices()
    }

    override fun updateVertices() {
        val borderWidth = border * frame.width
        val borderHeight = border * frame.height
        val edges = arrayOf(
            Rectangle(frame.left, frame.top, frame.left + borderWidth, frame.top - borderHeight),
            Rectangle(frame.left + borderWidth, frame.top, frame.right - borderWidth, frame.top - borderHeight),
            Rectangle(frame.right - borderWidth, frame.top, frame.right, frame.top - borderHeight),

            Rectangle(frame.left, frame.top - borderHeight, frame.left + borderWidth, frame.bottom + borderHeight),
            Rectangle(frame.left + borderWidth, frame.top - borderHeight, frame.right - borderWidth, frame.bottom + borderHeight),
            Rectangle(frame.right - borderWidth, frame.top - borderHeight, frame.right, frame.bottom + borderHeight),

            Rectangle(frame.left, frame.bottom + borderHeight, frame.left + borderWidth, frame.bottom),
            Rectangle(frame.left + borderWidth, frame.bottom + borderHeight, frame.right - borderWidth, frame.bottom),
            Rectangle(frame.right - borderWidth, frame.bottom + borderHeight, frame.right, frame.bottom)
        )
        println(factor)
        println(realFactor)
        val coords = arrayOf(
            Rectangle(0F, 0F, realFactor.x, -realFactor.y),
            Rectangle(realFactor.x, 0F, 1 - realFactor.x, -realFactor.y),
            Rectangle(1 - realFactor.x, 0F, 1F, -realFactor.y),

            Rectangle(0F, -realFactor.y, realFactor.x, realFactor.y - 1),
            Rectangle(realFactor.x, -realFactor.y, 1 - realFactor.x, realFactor.y - 1),
            Rectangle(1 - realFactor.x, -realFactor.y, 1F, realFactor.y - 1),

            Rectangle(0F, realFactor.y - 1, realFactor.x, -1F),
            Rectangle(realFactor.x, realFactor.y - 1, 1 - realFactor.x, -1F),
            Rectangle(1 - realFactor.x, realFactor.y - 1, 1F, -1F),
        )
        updateBuffer(2, coords.flatMap { it.toPointsArray().asIterable() }.toFloatArray(), edges.flatMap { it.toPointsArray().asIterable() }.toFloatArray())
    }

    override fun drawSelf () {
        image.bind()
        Script.setTexture(image)
        Script.drawMultiple(9)
        image.release()
    }
}
