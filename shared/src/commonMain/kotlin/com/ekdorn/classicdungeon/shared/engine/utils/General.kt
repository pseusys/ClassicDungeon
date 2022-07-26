package com.ekdorn.classicdungeon.shared.engine.utils

import com.ekdorn.classicdungeon.shared.IO
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt


/**
 * Function for clean output of Floats. It takes in account desired number of places before and after dot.
 * @param ints minimal length of integer part of the receiver (prepended with whitespaces)
 * @param floats number of decimal places to show (receiver is rounded to that number)
 * @return the String representation of the receiver up to numOfDec decimal places
 */
fun Float.str(ints: Int, floats: Int): String {
    val integerDigits = this.toInt()
    val floatDigits = ((this - integerDigits) * 10F.pow(floats)).roundToInt().absoluteValue
    return "${integerDigits.toString().padStart(ints)}.${floatDigits.toString().padEnd(floats, '0')}"
}


fun assert(bool: Boolean, message: () -> String) = if (!bool) {
    val text = message()
    IO.logger.a(text)
    throw RuntimeException(text)
} else Unit
