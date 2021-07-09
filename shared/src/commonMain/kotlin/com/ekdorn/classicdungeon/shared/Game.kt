package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.ui.ImageUI
import com.ekdorn.classicdungeon.shared.ui.LayoutUI
import com.ekdorn.classicdungeon.shared.ui.TextUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
internal object Game {
    private var elapsed = 0F
    val scope = CoroutineScope(Dispatchers.Default)

    private lateinit var root: LayoutUI
    private lateinit var splash: ImageUI

    fun resume () {}
    fun update () {
        val current = 0F //Clock.System.now().toEpochMilliseconds()
        root.update(current - elapsed)
        root.draw()
        elapsed = current
    }
    fun pause () {}


    fun splash (width: Int, height: Int) {
        root = LayoutUI(Rectangle(0F, 0F, width.toFloat(), height.toFloat()))
        splash = ImageUI("sample", Vector(0.1F, 0.1F), width = 0.5F)
        splash.parent = root
        root.children.add(splash)

        Input.onResized.add {
            root.resize(it.w, it.h)
            false
        }
    }

    fun start () {
        println("Game started!")
        root.children.remove(splash)
        val hello = TextUI(Vector(0F, 0F), "Hello, World!", "font", 0.5F, 0.2F)
        hello.parent = root
        root.children.add(hello)
    }

    fun end () {
        println("Game ended!")
    }
}
