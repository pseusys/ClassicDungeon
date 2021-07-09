package com.ekdorn.classicdungeon.shared.utils

import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector


internal val ImageFonts by lazy { hashMapOf(
    Pair("font", ImageFont("font")),
    //Pair("medium", ImageFont("font_medium")),
    //Pair("large", ImageFont("font_large"))
)}

internal class ImageFont (resource: String): HashMap<Char, Rectangle>(ALPHABET.size) {
    private companion object {
        val ALPHABET = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007FАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя".toCharArray().toTypedArray()
        val SPLIT = Color()
    }

    val texture = TextureCache.get(resource)
    val width = texture.image.width
    val height = texture.image.height

    init {
        var gap: Boolean
        var pastW = 0
        var charCounter = 0
        for (column in 0 until width) {
            gap = false
            for (row in 0 until height) gap = gap || (texture.image.getPixel(column, row) != SPLIT)
            if ((ALPHABET[charCounter] == ' ') && gap) {
                put(ALPHABET[charCounter], Rectangle(pastW, 0, column - 1, height))
                pastW = column - 1
                charCounter++
            } else if ((ALPHABET[charCounter] != ' ') && !gap) {
                put(ALPHABET[charCounter], Rectangle(pastW, 0, column, height))
                pastW = column
                charCounter++
            }
            if (charCounter == ALPHABET.size) break
        }
    }
}
