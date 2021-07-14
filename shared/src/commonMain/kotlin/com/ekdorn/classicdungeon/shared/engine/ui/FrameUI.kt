package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.generics.TextureCache
import com.ekdorn.classicdungeon.shared.engine.glextensions.Script
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle
import com.ekdorn.classicdungeon.shared.engine.maths.Vector

internal class FrameUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ResizableUI(initializer) {
    var frame = Rectangle(0F, 1F, 1F, 0F)
        set (v) {
            dirty = true
            field = v
        }

    var texture = TextureCache.get(TextureCache.NO_TEXTURE)

    var horizontal = true
        set (v) {
            dirty = true
            field = v
        }

    var vertical = true
        set (v) {
            dirty = true
            field = v
        }

    var border = Vector()
        set (v) {
            dirty = true
            field = v
        }


    init {
        texture = TextureCache.get(initializer.getOrElse("resource") { TextureCache.NO_TEXTURE } as String)
        frame = initializer.getOrElse("frame") { frame } as Rectangle
        horizontal = initializer.getOrElse("horizontal") { horizontal } as Boolean
        vertical = initializer.getOrElse("vertical") { vertical } as Boolean
        border = initializer.getOrElse("border") { border } as Vector
        updateVertices()
    }



    override fun draw () {
        super.draw()
        texture.bind()
        Script.setTexture(texture)
        Script.drawMultiple(9)
        texture.release()
    }



    override fun updateVertices() {
        val bord = Vector(border.x * frame.width, border.y * frame.height)
        val edges = arrayOf(
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

        val pxlbrd = Vector(texture.image.width * frame.width, texture.image.height * frame.height) * border / metrics
        val coords = arrayOf(
            Rectangle(0F, 0F, pxlbrd.x, -pxlbrd.y),
            Rectangle(pxlbrd.x, 0F, 1 - pxlbrd.x, -pxlbrd.y),
            Rectangle(1 - pxlbrd.x, 0F, 1F, -pxlbrd.y),

            Rectangle(0F, -pxlbrd.y, pxlbrd.x, pxlbrd.y - 1),
            Rectangle(pxlbrd.x, -pxlbrd.y, 1 - pxlbrd.x, pxlbrd.y - 1),
            Rectangle(1 - pxlbrd.x, -pxlbrd.y, 1F, pxlbrd.y - 1),

            Rectangle(0F, pxlbrd.y - 1, pxlbrd.x, -1F),
            Rectangle(pxlbrd.x, pxlbrd.y - 1, 1 - pxlbrd.x, -1F),
            Rectangle(1 - pxlbrd.x, pxlbrd.y - 1, 1F, -1F),
        )
        updateBuffer(2, coords.flatMap { it.toPointsArray().asIterable() }.toFloatArray(), edges.flatMap { it.toPointsArray().asIterable() }.toFloatArray())
    }
}
