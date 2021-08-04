package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.generics.TextureCache
import com.ekdorn.classicdungeon.shared.engine.glextensions.Script
import com.ekdorn.classicdungeon.shared.engine.lib.toFloatArray
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle
import com.ekdorn.classicdungeon.shared.engine.maths.Vector


internal class FrameUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ResizableUI(initializer) {
    public override var stretchW = super.stretchW
        set (v) {
            dirty = true
            field = v
        }

    public override var stretchH = super.stretchH
        set (v) {
            dirty = true
            field = v
        }


    var frame = initializer.getOrElse("frame") { Rectangle(0F, 1F, 1F, 0F) } as Rectangle
        set (v) {
            dirty = true
            field = v
        }

    var texture = TextureCache.get(initializer.getOrElse("resource") { TextureCache.NO_TEXTURE } as String)

    var border = initializer.getOrElse("border") { Vector() } as Vector
        set (v) {
            dirty = true
            field = v
        }


    init { updateVertices() }



    fun pixelBorder () = texture.image.metrics * frame.metrics * border * pixelation



    override fun draw () {
        super.draw()
        texture.bind()
        Script.setTexture(texture)
        Script.drawMultiple(9)
        texture.release()
    }



    override fun updateVertices() {
        super.updateVertices()
        val pixelBorder = pixelBorder() / metrics
        val vertices = listOf(
            Rectangle(0F, 0F, pixelBorder.x, -pixelBorder.y),
            Rectangle(pixelBorder.x, 0F, 1 - pixelBorder.x, -pixelBorder.y),
            Rectangle(1 - pixelBorder.x, 0F, 1F, -pixelBorder.y),

            Rectangle(0F, -pixelBorder.y, pixelBorder.x, pixelBorder.y - 1),
            Rectangle(pixelBorder.x, -pixelBorder.y, 1 - pixelBorder.x, pixelBorder.y - 1),
            Rectangle(1 - pixelBorder.x, -pixelBorder.y, 1F, pixelBorder.y - 1),

            Rectangle(0F, pixelBorder.y - 1, pixelBorder.x, -1F),
            Rectangle(pixelBorder.x, pixelBorder.y - 1, 1 - pixelBorder.x, -1F),
            Rectangle(1 - pixelBorder.x, pixelBorder.y - 1, 1F, -1F),
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

        updateBuffer(vertices.toFloatArray(), textures.toFloatArray())
    }
}
