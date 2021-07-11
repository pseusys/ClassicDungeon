package com.ekdorn.classicdungeon.shared.utils

internal class Animation (fps: Int, val looped: Boolean = false, vararg val order: Int) {
    val delay = 1000 / fps
}
