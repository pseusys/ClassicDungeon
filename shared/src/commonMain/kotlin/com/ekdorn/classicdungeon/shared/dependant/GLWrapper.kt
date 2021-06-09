package com.ekdorn.classicdungeon.shared.dependant

import kotlinx.coroutines.CoroutineDispatcher

// TODO: move dispatcher here when possible (https://youtrack.jetbrains.com/issue/KT-20427)

expect object GLWrapper {
    //expect val GL: OpenGLDispatcher
}
