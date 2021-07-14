package com.ekdorn.classicdungeon.shared.utils

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector


private object FontUtils {
    val ALPHABET = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007FАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя".toList()
    val SPLIT = Color()
}

internal enum class ImageFont (resource: String): MutableMap<Char, Rectangle> by HashMap(FontUtils.ALPHABET.size) {
    SMALL("font"), MEDIUM("font");

    val texture = TextureCache.get(resource)
    val width = texture.image.width.toInt()
    val height = texture.image.height.toInt()

    init {
        var gap: Boolean
        var pastW = 0
        var charCounter = 0
        for (column in 0 until width) {
            gap = false
            for (row in 0 until height) gap = gap || (texture.image.getPixel(column, row) != FontUtils.SPLIT)
            if ((FontUtils.ALPHABET[charCounter] == ' ') && gap) {
                put(FontUtils.ALPHABET[charCounter], Rectangle(pastW, 0, column - 1, height))
                pastW = column - 1
                charCounter++
            } else if ((FontUtils.ALPHABET[charCounter] != ' ') && !gap) {
                put(FontUtils.ALPHABET[charCounter], Rectangle(pastW, 0, column, height))
                pastW = column
                charCounter++
            }
            if (charCounter == FontUtils.ALPHABET.size) break
        }
    }
}
