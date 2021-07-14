package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector


// FINAL
internal open class ImageUI (initializer: Map<String, *> = hashMapOf<String, Any>()): WidgetUI(initializer) {
    private companion object { val delay = Rectangle(0F, 0F, 1F, -1F).toPointsArray() }



    @Implicit override var dimens = super.dimens
        get () = if (parent != null) metrics / parentMetrics()!! else field

    var texture = TextureCache.get(TextureCache.NO_TEXTURE)

    var frame: Rectangle =  Rectangle(0F, 1F, 1F, 0F)
        set (v) {
            metrics = Vector(texture.image.width * v.width, texture.image.height * v.height)
            dirty = true
            field = v
        }

    var mirroredH: Boolean = false
        set (v) {
            dirty = true
            field = v
        }

    var mirroredV: Boolean = false
        set (v) {
            dirty = true
            field = v
        }


    init {
        texture = TextureCache.get(initializer.getOrElse("resource") { TextureCache.NO_TEXTURE } as String)
        frame = initializer.getOrElse("frame") { frame } as Rectangle
        updateVertices()
    }



    override fun draw () {
        super.draw()
        texture.bind()
        Script.setTexture(texture)
        Script.drawSingle()
        texture.release()
    }



    final override fun updateVertices () {
        super.updateVertices()

        val x = if (mirroredH) Pair(frame.right, frame.left)
        else Pair(frame.left, frame.right)

        val y = if (mirroredV) Pair(frame.bottom, frame.top)
        else Pair(frame.top, frame.bottom)

        val textureVertices = Rectangle(x.first, y.first, x.second, y.second)
        updateBuffer(2, delay, textureVertices.toPointsArray())
    }
}
