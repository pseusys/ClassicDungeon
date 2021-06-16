package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.dependant.OpenGLDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

internal object Game: Assigned {
    private val elapsed = 0
    val scope = CoroutineScope(Dispatchers.Default)
    val gl = OpenGLDispatcher()


    fun resume () {}
    fun update () {}
    fun pause () {}


    override fun gameStarted() {
        println("Game started!")
    }

    override fun gameEnded() {
        println("Game ended!")
    }
}
