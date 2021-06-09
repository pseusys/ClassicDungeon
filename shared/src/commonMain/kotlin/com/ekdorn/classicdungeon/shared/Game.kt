package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.dependant.OpenGLDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

internal object Game {
    val elapsed = 0
    val scope = CoroutineScope(Dispatchers.Default)
    val gl = OpenGLDispatcher()
}
