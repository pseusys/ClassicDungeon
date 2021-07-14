package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.glextensions.Script
import com.ekdorn.classicdungeon.shared.engine.maths.Vector
import com.ekdorn.classicdungeon.shared.engine.utils.ImageFont


internal class TextUI (pos: Vector, txt: String, private val font: ImageFont, width: Float, var lineHeight: Float): ResizableUI(hashMapOf<String, Any>()) {
    enum class ALIGNMENT {
        START, CENTER, END, FILL
    }



    constructor (pos: Vector, txt: String, font: String, width: Float, lineHeight: Float): this(pos, txt, ImageFont.SMALL, width, lineHeight) {
        updateVertices()
    }

    private var rat = 1F

    var multiline = true
    var text = txt
        set (v) {
            field = v
            updateVertices()
        }


    override fun resize (newMetrics: Vector) {
        //rat = ratio
        updateVertices()
    }

    override fun updateVertices () {
        /*val past = Vector()
        val verticesList = mutableListOf<Rectangle>()
        val texturesList = mutableListOf<Rectangle>()

        for (word in text.split(' ', '\t', '\n')) {
            val wordPast = past.copy()
            val wordVertices = mutableListOf<Rectangle>()
            val wordTextures = mutableListOf<Rectangle>()

            val addChar = { char: Char ->
                val ch = font[char]!!
                val charWidth = (ch.ratio * lineHeight) / (metrics.x * rat)
                wordVertices.add(Rectangle(wordPast.x, wordPast.y, wordPast.x + charWidth, wordPast.y - 1))
                wordTextures.add(Rectangle(ch.left / font.width, 1F, ch.right / font.width, 0F))
                wordPast.x += charWidth
            }

            for (char in word.toCharArray()) addChar(char)
            val pureLen = wordPast.x
            addChar(' ')

            if (multiline && (past.x > 0) && (pureLen > 1)) {
                wordVertices.forEach {
                    it.left -= past.x
                    it.right -= past.x
                    it.top -= 1
                    it.bottom -= 1
                }
                past.apply { y -= 1; x = wordPast.x - x }
            } else past.x = wordPast.x

            verticesList.addAll(wordVertices)
            texturesList.addAll(wordTextures)
        }


        val heights = -(past.y - 1)
        verticesList.forEach { it.apply { top /= heights; bottom /= heights } }
        metrics.y = heights * lineHeight

        val vertices = verticesList.flatMap { it.toPointsArray().asIterable() }.toFloatArray()
        val textures = texturesList.flatMap { it.toPointsArray().asIterable() }.toFloatArray()
        updateBuffer(2, vertices, textures)*/
    }

    override fun draw () {
        super.draw()
        font.texture.bind()
        Script.setTexture(font.texture)
        //Script.drawMultiple(text.length)
        font.texture.release()
    }
}