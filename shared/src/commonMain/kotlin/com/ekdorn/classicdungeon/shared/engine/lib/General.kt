package com.ekdorn.classicdungeon.shared.engine.lib

import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt


/**
 *
 * @param ints minimal length of integer part of the receiver (prepended with whitespaces)
 * @param floats number of decimal places to show (receiver is rounded to that number)
 * @return the String representation of the receiver up to numOfDec decimal places
 */
fun Float.str(ints: Int, floats: Int): String {
    val integerDigits = this.toInt()
    val floatDigits = ((this - integerDigits) * 10F.pow(floats)).roundToInt().absoluteValue
    return "${integerDigits.toString().padStart(ints)}.${floatDigits.toString().padEnd(floats, '0')}"
}

fun Char.isEmpty () = (this == ' ') || (this == '\n') || (this == '\t')