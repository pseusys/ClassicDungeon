package com.ekdorn.classicdungeon.shared.dependant

import kotlinx.coroutines.CoroutineDispatcher


expect class OpenGLDispatcher (): CoroutineDispatcher {
}