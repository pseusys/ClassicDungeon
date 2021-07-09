package com.ekdorn.classicdungeon.shared.lib

import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Return the float receiver as a string display with numOfDec after the decimal (rounded)
 * (e.g. 35.72 with numOfDec = 1 will be 35.7, 35.78 with numOfDec = 2 will be 35.80)
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

fun <E> MutableCollection<E>.addAll (vararg values: E) = addAll(values)
