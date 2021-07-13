package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.ui.*
import com.ekdorn.classicdungeon.shared.ui.ClipUI
import com.ekdorn.classicdungeon.shared.ui.FrameUI
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
        splash = ImageUI("sample", Vector(0.1F, 0.1F), 0.8F, 0.8F)
        root.add(splash)

        Input.onResized.add {
            root.resize(it.w, it.h)
            false
        }
    }

    fun start () {
        println("Game started!")
        root.remove(splash)

        val frame = FrameUI("chrome", Rectangle(0.2F, 0.1F, 0.6F, 0.8F), Rectangle(0F, 1F, 0.171875F, 0.65625F))

        val hello = TextUI(Vector(0.1F, 0.1F), "Please, enjoy this fine animation:", "font", 0.8F, 0.075F)
        hello.multiplyColor(Color(1F, 1F, 0F, 1F))
        frame.add(hello)

        val bee = ClipUI("bee", Vector(0.15F, 0.4F), 0.3F, 0.5F)
        bee.play(20, true, 7, 8, 9, 10)
        frame.add(bee)

        val bee1 = ClipUI("bee", Vector(0.55F, 0.4F), 0.3F, 0.5F)
        bee1.play(20, true, 7, 8, 9, 10)
        frame.add(bee1)

        root.add(frame)
    }

    fun end () {
        println("Game ended!")
    }
}
