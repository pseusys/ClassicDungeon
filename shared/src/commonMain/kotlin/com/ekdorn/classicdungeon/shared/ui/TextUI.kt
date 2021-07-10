package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.lib.isEmpty
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.utils.ImageFont
import com.ekdorn.classicdungeon.shared.utils.ImageFonts


internal class TextUI (pos: Vector, txt: String, private val font: ImageFont, width: Float, var lineHeight: Float): PreservingUI(pos, width, 1F) {
    constructor (pos: Vector, txt: String, font: String, width: Float, lineHeight: Float): this(pos, txt, ImageFonts[font]!!, width, lineHeight) {
        updateVertices()
    }

    private var ratio = 1F
    private var textLen = txt.length
    var text = txt


    override fun parentalResize (pixelWidth: Int, pixelHeight: Int) {
        ratio = pixelHeight.toFloat() / pixelWidth.toFloat()
        updateVertices()
    }

    override fun updateVertices () {
        val past = Vector()
        val verticesList = mutableListOf<Float>()
        val texturesList = mutableListOf<Float>()
        textLen = text.length

        for (char in text.toCharArray()) {
            val ch = font[char]!!
            val charWidth = (ch.width() * lineHeight * ratio) / (metrics.x * ch.height())

            if (char.isEmpty() && (past.x + charWidth > 1)) {
                past.y += lineHeight
                past.x = 0F
                textLen--
                continue
            }

            verticesList.addAll(Rectangle(past.x, -past.y, past.x + charWidth, -(lineHeight + past.y)).toPointsArray().toTypedArray())
            texturesList.addAll(Rectangle(ch.left / font.width, 1F, ch.right / font.width, 0F).toPointsArray().toTypedArray())
            past.x += charWidth
        }

        updateBuffer(2, verticesList.toFloatArray(), texturesList.toFloatArray())
    }

    override fun draw () {
        super.draw()
        font.texture.bind()
        Script.setTexture(font.texture)
        Script.drawMultiple(textLen)
        font.texture.release()
    }
}
