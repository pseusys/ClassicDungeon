package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.generics.Assigned
import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.ui.ImageUI
import com.ekdorn.classicdungeon.shared.ui.LayoutUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
internal object Game: Assigned {
    private var elapsed = 0F
    val scope = CoroutineScope(Dispatchers.Default)

    private lateinit var root: LayoutUI

    fun resume () {}
    fun update () {
        val current = 0F //Clock.System.now().toEpochMilliseconds()
        root.update(current - elapsed)
        root.draw()
        elapsed = current
    }
    fun pause () {}


    override suspend fun gameStarted (screenWidth: Int, screenHeight: Int) {
        println("Game started!")
        Input.onResized.add {
            root.delete()
            afterStarted(it.w, it.h)
            false
        }
    }

    fun afterStarted (width: Int, height: Int) {
        root = LayoutUI(Rectangle(0F, 0F, width.toFloat(), height.toFloat()))
        root.children.add(ImageUI("sample", Rectangle(-1F, 1F, 1F, -1F)))
    }

    override suspend fun gameEnded() {
        println("Game ended!")
    }
}
