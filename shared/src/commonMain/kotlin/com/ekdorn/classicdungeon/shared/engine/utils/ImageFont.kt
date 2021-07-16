package com.ekdorn.classicdungeon.shared.engine.utils

import com.ekdorn.classicdungeon.shared.engine.generics.TextureCache
import com.ekdorn.classicdungeon.shared.engine.maths.Color
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle


private object FontUtils {
    const val ALPHABET = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007FАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя"
    val SPLIT = Color()
}

internal enum class ImageFont (resource: String): MutableMap<Char, Rectangle> by HashMap(FontUtils.ALPHABET.length) {
    SMALL("font"), MEDIUM("font");

    val texture = TextureCache.get(resource)
    val width = texture.image.width
    val height = texture.image.height

    init {
        var gap: Boolean
        var past = 0F
        val half = 0.5F / width
        var charCounter = 0
        for (column in 0 until width.toInt()) {
            gap = false
            for (row in 0 until height.toInt()) gap = gap || (texture.image.getPixel(column, row) != FontUtils.SPLIT)
            if ((FontUtils.ALPHABET[charCounter] == ' ') && gap) {
                put(FontUtils.ALPHABET[charCounter], Rectangle(past / width + half, 1F, (column - 1) / width + half, 0F))
                past = column - 1F
                charCounter++
            } else if ((FontUtils.ALPHABET[charCounter] != ' ') && !gap) {
                put(FontUtils.ALPHABET[charCounter], Rectangle(past / width + half, 1F, column / width + half, 0F))
                past = column.toFloat()
                charCounter++
            }
            if (charCounter == FontUtils.ALPHABET.length) break
        }
    }
}
