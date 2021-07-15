package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.glextensions.Script
import com.ekdorn.classicdungeon.shared.engine.lib.toFloatArray
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle
import com.ekdorn.classicdungeon.shared.engine.maths.Vector
import com.ekdorn.classicdungeon.shared.engine.utils.ImageFont


internal class TextUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ResizableUI(hashMapOf<String, Any>()) {
    enum class ALIGNMENT {
        START, CENTER, END, FILL
    }



    @Implicit private var textLength = 0


    var font = ImageFont.MEDIUM
        set (v) {
            dirty = true
            field = v
        }

    var multiline = true
        set (v) {
            dirty = true
            field = v
        }

    var text = ""
        set (v) {
            dirty = true
            field = v.replace("\t", "    ")
        }


    init {
        stretchY = false

        font = ImageFont.valueOf(initializer.getOrElse("font") { font.name } as String)
        multiline = initializer.getOrElse("multiline") { multiline } as Boolean
        text = initializer.getOrElse("text") { text } as String
        updateVertices()
    }



    override fun draw () {
        super.draw()
        font.texture.bind()
        Script.setTexture(font.texture)
        Script.drawMultiple(textLength)
        font.texture.release()
    }


    override fun translate (parentCoords: Vector, parentMetrics: Vector) {
        if (dirty) updateVertices()
        super.translate(parentCoords, parentMetrics)
    }

    override fun updateVertices () {
        super.updateVertices()
        if (parent == null) return

        val vertices = mutableListOf<Rectangle>()
        val textures = mutableListOf<Rectangle>()

        if (!multiline) text.replace('\n', ' ')
        val space = font.texture.image.metrics * font[' ']!!.metrics * pixelation / Vector(metrics.x, parentMetrics()!!.y)

        val lines = text.split('\n').filter { it.isNotEmpty() }.toMutableList()
        var iterator = 0
        do {
            if (lines.isEmpty()) break

            var past = 0F
            val lineVertices = mutableListOf<Rectangle>()
            val lineTextures = mutableListOf<Rectangle>()

            val words = lines[iterator].split(' ')
            for (word in words.withIndex()) {
                var wordPast = 0F
                val wordVertices = mutableListOf<Rectangle>()
                val wordTextures = mutableListOf<Rectangle>()

                for (char in word.value) {
                    val ch = font[char]!!
                    wordTextures.add(ch)

                    val charWidth = font.width * ch.width * pixelation / metrics.x
                    wordVertices.add(Rectangle(wordPast, 0F, wordPast + charWidth, -1F))
                    wordPast += charWidth
                }

                if (multiline && (past > 0) && (past + wordPast > 1)) {
                    lines.add(iterator + 1, words.subList(word.index, words.size).joinToString(" "))
                    break
                } else {
                    wordTextures.add(font[' ']!!)
                    wordVertices.add(Rectangle(wordPast, 0F, wordPast + space.x, -1F))
                    wordPast += space.x

                    wordVertices.forEach { it.translate(x = past) }
                    past += wordPast
                }

                lineVertices.addAll(wordVertices)
                lineTextures.addAll(wordTextures)
            }

            vertices.addAll(lineVertices.map { it.translate(y = -iterator.toFloat()) })
            textures.addAll(lineTextures)

            iterator++
        } while (iterator < lines.size)

        textLength = vertices.size
        vertices.forEach { it.vertical /= lines.size.toFloat() }
        dimens.y = lines.size * space.y

        updateBuffer(2, vertices.toFloatArray(), textures.toFloatArray())
    }
}
