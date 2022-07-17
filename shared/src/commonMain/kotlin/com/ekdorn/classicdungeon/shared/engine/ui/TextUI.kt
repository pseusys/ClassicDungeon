package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.gl.extensions.Script
import com.ekdorn.classicdungeon.shared.engine.atomic.Rectangle
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.gl.extensions.ImageFont
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


/**
 * TextUI - number of images, each representing a letter, together forming text.
 * May be stretched in different ways, single- or multiline.
 */
@Serializable
internal class TextUI: ResizableUI() {
    /**
     * Types of internal text stretching:
     * - START - letters aligned to left
     * - CENTER - letters aligned to center
     * - END - letters aligned to right
     * - FILL - letters stretched to fill whole width
     */
    @Serializable
    enum class ALIGNMENT {
        START, CENTER, END, FILL
    }


    override var parent: LayoutUI? = null
        set(v) {
            field = v
            dirty = true
        }


    /**
     * Property multiline - whether text should be single- or multiline.
     * True by default.
     */
    var multiline = true
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Property text - the text to draw.
     * "" by default.
     */
    var text = ""
        set (v) {
            dirty = true
            field = v
        }

    /**
     * Property textAlignment - type of internal text stretching to use.
     * ALIGNMENT.CENTER by default.
     */
    var textAlignment = ALIGNMENT.CENTER
        set (v) {
            dirty = true
            field = v
        }

    var fontSource = ImageFont.MEDIUM.name
        set (v) {
            field = v
            font = ImageFont.valueOf(field)
        }


    /**
     * Number of letters actually drawn (with unnecessary whitespaces omitted).
     */
    @Transient private var textLength = 0

    /**
     * Property font - font to take letters from.
     * ImageFont.MEDIUM by default.
     */
    @Transient private var font = ImageFont.valueOf(fontSource)
        set (v) {
            dirty = true
            field = v
        }


    init {
        stretchH = false
        updateVertices()
    }


    /**
     * General line-iterating function for updateVertices.
     * Iterates lines, allowing extra line insertion from processing function if line exceeds nax length.
     * @param iterating function processing each new line.
     */
    private fun MutableList<String>.iterateLines (iterating: (Int) -> Float): Float {
        var maxPast = 0F
        var iterator = 0
        do {
            if (isEmpty()) break
            val past = iterating(iterator)
            if (past > maxPast) maxPast = past
            iterator++
        } while (iterator < size)
        return maxPast
    }

    override fun draw () {
        super.draw()
        font.texture.bind()
        Script.setTexture(font.texture)
        Script.drawMultiple(textLength)
        font.texture.release()
    }

    override fun translate (parentCoords: Vector, parentMetrics: Vector) {
        dimens.y = metrics.y / parentMetrics.y
        super.translate(parentCoords, parentMetrics)
    }

    override fun updateVertices () {
        super.updateVertices()
        if (parent == null) return

        val vertices = mutableListOf<Rectangle>()
        val textures = mutableListOf<Rectangle>()

        text = text.replace("\t", "    ")
        if (!multiline) text = text.replace('\n', ' ')

        // Lines except empty.
        val lines = text.split('\n').filter { it.isNotEmpty() }.toMutableList()
        val maxPast = lines.iterateLines { index ->
            // Offset from the beginning of the line in width percent.
            var past = 0F

            val lineVertices = mutableListOf<Rectangle>()
            val lineTextures = mutableListOf<Rectangle>()

            // Words of the line.
            val words = lines[index].split(' ')
            for (word in words.withIndex()) {
                // Offset from the beginning of the line in width percent.
                var wordPast = 0F
                val wordVertices = mutableListOf<Rectangle>()
                val wordTextures = mutableListOf<Rectangle>()

                // Adding char vertices rect and texture rect, NB! chars have const height.
                for (char in word.value) {
                    val ch = font[char]!!
                    wordTextures.add(ch)

                    val charWidth = font.width * ch.width * pixelation / metrics.x
                    wordVertices.add(Rectangle(wordPast, 0F, wordPast + charWidth, -1F))
                    wordPast += charWidth
                }

                // If new line width exceeds view width, flushing remaining of the line as a next line.
                if (multiline && (past > 0) && (past + wordPast > 1)) {
                    lines.add(index + 1, words.subList(word.index, words.size).joinToString(" "))
                    break
                } else {
                    // Translating word to the line end.
                    // Adding space before the word if it is not the first word of the line.
                    if (word.index != 0) {
                        val ch = font[' ']!!
                        wordTextures.add(0, ch)

                        val charWidth = font.width * ch.width * pixelation / metrics.x
                        wordVertices.forEach { it.translate(x = past + charWidth) }
                        wordVertices.add(0, Rectangle(past, 0F, past + charWidth, -1F))
                        wordPast += charWidth
                    } else wordVertices.forEach { it.translate(x = past) }

                    past += wordPast
                }

                lineVertices.addAll(wordVertices)
                lineTextures.addAll(wordTextures)
            }

            // Remainder of space left on the line.
            val rem = 1 - past
            // Moving line coords to appropriate height.
            lineVertices.forEach { it.translate(y = -index.toFloat()) }
            vertices.addAll(when (textAlignment) {
                ALIGNMENT.START -> lineVertices
                ALIGNMENT.CENTER -> lineVertices.map { it.apply { horizontal += rem / 2 } }
                ALIGNMENT.END -> lineVertices.map { it.apply { horizontal += rem } }
                ALIGNMENT.FILL -> {
                    // Finding all space symbol indexes.
                    val spaces = lineTextures.indices.count { lineTextures[it] == font[' ']!! }
                    // Const to add to char x.
                    var sp = 0F
                    lineVertices.mapIndexed { ind, rect ->
                        // For regular chars, moving along x by sp.
                        if (lineTextures[ind] != font[' ']!!) rect.apply { horizontal += sp }
                        // For spaces, moving left coordinate along x by sp and right - by sp + rem / count of spaces.
                        // From now on sp = sp + rem / count of spaces.
                        else rect.apply { left += sp; sp += rem / spaces; right += sp }
                    }
                }
            })
            textures.addAll(lineTextures)
            past
        }

        textLength = vertices.size

        // Because this view is horizontally-resizeable only, setting new metrics.
        metrics.y = font.height * lines.size * pixelation

        // Dividing all y coords by lines number, to fit in new dimens.y.
        vertices.forEach { it.vertical /= lines.size.toFloat() }
        dimens = Vector(if (dimens.x == 0F) maxPast else dimens.x, metrics.y / parentMetrics()!!.y)

        buffer.fill(vertices, textures)
    }
}
