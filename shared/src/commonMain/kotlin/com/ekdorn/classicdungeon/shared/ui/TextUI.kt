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

    var multiline = true
    var text = txt
        set (v) {
            field = v
            updateVertices()
        }


    override fun parentalResize (pixelWidth: Int, pixelHeight: Int) {
        if (preserving) {
            ratio = pixelHeight.toFloat() / pixelWidth.toFloat()
            updateVertices()
        }
    }

    override fun updateVertices () {
        val past = Vector()
        val verticesList = mutableListOf<Rectangle>()
        val texturesList = mutableListOf<Rectangle>()
        textLen = text.length

        for (char in text.toCharArray()) {
            val ch = font[char]!!
            val charWidth = (ch.width() * lineHeight * ratio) / (metrics.x * ch.height())

            if (multiline && char.isEmpty() && (past.x + charWidth > 1)) {
                past.apply { y -= 1; x = 0F }
                textLen--
                continue
            }

            verticesList.add(Rectangle(past.x, past.y, past.x + charWidth, past.y - 1))
            texturesList.add(Rectangle(ch.left / font.width, 1F, ch.right / font.width, 0F))
            past.x += charWidth
        }

        val heights = -(past.y - 1)
        verticesList.forEach { it.apply { top /= heights; bottom /= heights } }
        metrics.y = heights * lineHeight

        val vertices = verticesList.flatMap { it.toPointsArray().asIterable() }.toFloatArray()
        val textures = texturesList.flatMap { it.toPointsArray().asIterable() }.toFloatArray()
        updateBuffer(2, vertices, textures)
    }

    override fun draw () {
        super.draw()
        font.texture.bind()
        Script.setTexture(font.texture)
        Script.drawMultiple(textLen)
        font.texture.release()
    }
}
