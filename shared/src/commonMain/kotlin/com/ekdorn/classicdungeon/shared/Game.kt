package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.ui.ClipUI
import com.ekdorn.classicdungeon.shared.ui.ImageUI
import com.ekdorn.classicdungeon.shared.ui.RootUI
import com.ekdorn.classicdungeon.shared.ui.TextUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
internal object Game {
    private var elapsed = 0
    val scope = CoroutineScope(Dispatchers.Default)

    private lateinit var root: RootUI
    private lateinit var splash: ImageUI

    fun resume () {}
    fun update () {
        val current = Clock.System.now().toEpochMilliseconds().toInt()
        root.update(current - elapsed)
        GLFunctions.clear()
        root.draw()
        elapsed = current
    }
    fun pause () {}


    fun splash (width: Int, height: Int) {
        root = RootUI(Rectangle(0F, 0F, 1F, 1F), width, height)
        splash = ImageUI("sample", Vector(0.1F, 0.1F), width = 0.5F)
        root.add(splash)

        Input.onResized.add {
            root.resize(it.w, it.h)
            false
        }
    }

    fun start () {
        println("Game started!")
        root.remove(splash)

        val hello = TextUI(Vector(0F, 0F), "Hello, World!", "font", 0.5F, 0.2F)
        hello.multiplyColor(Color(1F, 1F, 0F, 1F))
        root.add(hello)

        val bee = ClipUI("bee", Vector(0F, 0.3F), height = 0.2F)
        bee.play(20, true, 7, 8, 9, 10)
        root.add(bee)
    }

    fun end () {
        println("Game ended!")
    }
}
