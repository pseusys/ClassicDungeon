package com.ekdorn.classicdungeon.shared.engine

import com.ekdorn.classicdungeon.shared.Input
import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.engine.generics.TextureCache
import com.ekdorn.classicdungeon.shared.engine.maths.Color
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle
import com.ekdorn.classicdungeon.shared.engine.maths.Vector
import com.ekdorn.classicdungeon.shared.engine.ui.*
import com.ekdorn.classicdungeon.shared.engine.ui.ClipUI
import com.ekdorn.classicdungeon.shared.engine.ui.FrameUI
import com.ekdorn.classicdungeon.shared.engine.ui.ImageUI
import com.ekdorn.classicdungeon.shared.engine.ui.LayoutUI
import com.ekdorn.classicdungeon.shared.engine.ui.RootUI
import com.ekdorn.classicdungeon.shared.engine.ui.WidgetUI
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
        splash = ImageUI()
        splash.anchor = Vector(0.5F, 0.5F)
        splash.pixelation = 4F
        root.add(splash)

        Input.onResized.add {
            root.resize(it.w, it.h)
            false
        }
    }

    fun start () {
        println("Game started!")
        root.remove(splash)
        splash.delete()

        val container = LayoutUI()
        container.addColor(Color(0, 1, 0, 1))
        container.anchor = Vector(0.5F, 0.5F)
        container.dimens = Vector(0.6F, 0.8F)

        val frame = FrameUI()
        frame.anchor = Vector(0F, 0F)
        frame.verticalAlignment = WidgetUI.ALIGNMENT.START
        frame.horizontalAlignment = WidgetUI.ALIGNMENT.START
        frame.dimens = Vector(1F, 1F)
        frame.texture = TextureCache.get("chrome")
        frame.pixelation = 8F
        frame.frame = Rectangle(0F, 1F, 0.171875F, 0.65625F)
        frame.border = Vector(0.31818181818F, 0.31818181818F)
        container.add(frame)

        val bee = ClipUI()
        bee.anchor = Vector(0.5F, 0.5F)
        bee.texture = TextureCache.getAtlas<Int>("bee")
        bee.pixelation = 8F
        bee.play(20, true, 7, 8, 9, 10)
        container.add(bee)

        root.add(container)

        /*
        val hello = TextUI(Vector(0.1F, 0.1F), "Please, enjoy this fine animation:", "font", 0.8F, 0.075F)
        hello.multiplyColor(Color(1F, 1F, 0F, 1F))
        frame.add(hello)

        val bee1 = ClipUI("bee", Vector(0.55F, 0.4F), 0.3F, 0.5F)
        bee1.play(20, true, 7, 8, 9, 10)
        frame.add(bee1)
        */
    }

    fun end () {
        println("Game ended!")
    }
}
