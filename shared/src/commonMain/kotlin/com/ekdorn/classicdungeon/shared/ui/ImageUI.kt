package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.generics.Clonable
import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.glwrapper.ImageTexture
import com.ekdorn.classicdungeon.shared.glwrapper.Script
import com.ekdorn.classicdungeon.shared.maths.Rectangle


internal class ImageUI private constructor (): WidgetUI(0.0, 0.0, 0.0, 0.0), Clonable<ImageUI> {
    constructor(resource: String): this() {
        texture(resource)
    }

    constructor (resource: String, rect: Rectangle): this(resource) {
        frame(rect)
    }

    private lateinit var texture: ImageTexture
    private lateinit var coverPercent: Rectangle

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


    override fun clone(): ImageUI {
        val clone = ImageUI()
        clone.texture = texture
        clone.coverPercent = coverPercent
        return clone
    }


    fun texture (resource: String) {
        texture = TextureCache.get(resource)
        frame(Rectangle(0.0, 0.0, 1.0, 1.0))
    }

    fun frame (rect: Rectangle) {
        coverPercent = rect
        width = rect.width() * texture.width()
        height = rect.height() * texture.height()
        updateVertices()
    }


    private fun updateVertices () {
        val x = if (mirroredH) Pair(coverPercent.left, coverPercent.right)
        else Pair(coverPercent.right, coverPercent.left)

        val y = if (mirroredV) Pair(coverPercent.bottom, coverPercent.top)
        else Pair(coverPercent.top, coverPercent.bottom)

        textureVertices = Rectangle(x.first, y.first, x.second, y.second)
    }

    @kotlin.ExperimentalUnsignedTypes
    override fun draw() {
        super.draw()

        texture.bind()

        // Camera

        Script.setTexture(texture)
        Script.setModel(model)
        Script.setMaterial(material)
        Script.setAmbient(ambient)

        Script.drawSingle(rect(), textureVertices)
    }
}
