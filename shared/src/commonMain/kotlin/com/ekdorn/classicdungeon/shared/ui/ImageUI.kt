package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.glwrapper.Camera
import com.ekdorn.classicdungeon.shared.glwrapper.ImageTexture
import com.ekdorn.classicdungeon.shared.glwrapper.Script
import com.ekdorn.classicdungeon.shared.maths.Matrix
import com.ekdorn.classicdungeon.shared.maths.Rectangle


// TODO: move GL + buffer from widget to other class
internal class ImageUI private constructor (rect: Rectangle): ElementUI(rect) {
    init {
        Script.createBuffer(this, 2 * 4 * Float.SIZE_BYTES)
    }

    constructor (resource: String, rect: Rectangle, frame: Rectangle): this(rect) {
        texture(resource)
        frame(frame)
        updateVertices()
    }

    constructor (resource: String, rect: Rectangle): this(resource, rect, Rectangle(0F, 1F, 1F, 0F))

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


    override fun clone (): ImageUI {
        val clone = ImageUI(rect())
        clone.texture = texture
        clone.frame = frame
        return clone
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
        println(rect().toPointsArray())
        println(textureVertices.toPointsArray())
        Script.updateBuffer(this, 2, rect().toPointsArray(), textureVertices.toPointsArray())
    }

    override fun draw () {
        texture.bind()

        Script.setCamera(Camera.uiCamera())

        Script.setTexture(texture)
        Script.setModel(model)
        Script.setMaterial(material)
        Script.setAmbient(ambient)

        Script.drawSingle(this)

        texture.release()
    }

    override fun delete () {
        Script.deleteBuffer(this)
    }
}
