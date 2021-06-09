package com.ekdorn.classicdungeon.shared.dependant

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

actual class OpenGLDispatcher actual constructor(): CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        TODO("Not yet implemented")
    }
}