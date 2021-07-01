package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.generics.Assigned
import com.ekdorn.classicdungeon.shared.dependant.GLFunctions
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.ui.ImageUI
import com.ekdorn.classicdungeon.shared.ui.LayoutUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
internal object Game: Assigned {
    private var elapsed = 0.0
    val scope = CoroutineScope(Dispatchers.Default)

    val root = LayoutUI()

    fun resume () {}
    fun update () {
        val current = 0.0 //Clock.System.now().toEpochMilliseconds()
        root.update(current - elapsed)
        root.draw()
        elapsed = current.toDouble()
    }
    fun pause () {}


    override suspend fun gameStarted() {
        println("Game started!")
        GLFunctions.setup()
        root.children.add(ImageUI("sample", Rectangle(-1.0, 1.0, 1.0, -1.0)))
    }

    override suspend fun gameEnded() {
        println("Game ended!")
    }
}
