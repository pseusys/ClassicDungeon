package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.utils.ImageFont
import com.ekdorn.classicdungeon.shared.utils.ImageFonts


internal class TextUI (pos: Vector, txt: String, private val font: ImageFont, width: Float, var lineHeight: Float): PreservingUI(pos, width, 1F) {
    constructor (pos: Vector, txt: String, font: String, width: Float, lineHeight: Float): this(pos, txt, ImageFonts[font]!!, width, lineHeight) {
        updateVertices()
    }

    var text = txt


    override fun parentalResize (pixelWidth: Int, pixelHeight: Int) {}

    fun updateVertices () {
        var past = Vector()
        val verticesList = mutableListOf<Float>()
        val texturesList = mutableListOf<Float>()

        for (char in text.toCharArray()) {
            val ch = font[char]!!
            val charWidth = ch.width() * lineHeight / ch.height()
            verticesList.addAll(Rectangle(past.x, past.y, past.x + charWidth, -lineHeight).toPointsArray().toTypedArray())
            texturesList.addAll(Rectangle(ch.left / font.width, 1F, ch.right / font.width, 0F).toPointsArray().toTypedArray())
            past += Vector(charWidth, 0F)
        }

        updateBuffer(2, verticesList.toFloatArray(), texturesList.toFloatArray())
    }

    override fun draw () {
        super.draw()
        font.texture.bind()
        Script.setTexture(font.texture)
        Script.drawMultiple(text.length)
        font.texture.release()
    }
}
