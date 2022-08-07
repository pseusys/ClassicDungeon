package com.ekdorn.classicdungeon.shared.engine.utils

import com.ekdorn.classicdungeon.shared.IO


fun assert(bool: Boolean, message: () -> String) = if (!bool) {
    val text = message()
    IO.logger.a(text)
    throw Exception(text)
} else Unit

fun except(message: String) {
    IO.logger.e(message)
    throw Exception(message)
}
