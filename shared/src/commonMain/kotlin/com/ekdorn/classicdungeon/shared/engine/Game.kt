package com.ekdorn.classicdungeon.shared.engine

import com.ekdorn.classicdungeon.shared.Input
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
        root.enter(current - elapsed)
        elapsed = current
    }
    fun pause () {}


    fun splash (width: Int, height: Int) {
        root = RootUI(width, height)
        splash = ImageUI()
        splash.anchor = Vector(0.5F, 0.5F)
        splash.pixelation = 8F
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
        container.addColor(Color(0x00FF00FFU))
        container.anchor = Vector(0.5F, 0.5F)
        container.dimens = Vector(0.6F, 0.8F)

        val frame = FrameUI()
        frame.anchor = Vector(0F, 0F)
        frame.verticalAlignment = WidgetUI.ALIGNMENT.START
        frame.horizontalAlignment = WidgetUI.ALIGNMENT.START
        frame.dimens = Vector(1F, 1F)
        frame.texture = TextureCache.get("chrome")
        frame.pixelation = 8F
        frame.pixelFrame = Rectangle(1F, 63F, 21F, 43F)
        frame.pixelBorder = Vector(6F, 6F)
        container.background = frame

        val hello = TextUI()
        hello.anchor = Vector(0.5F, 0F)
        hello.verticalAlignment = WidgetUI.ALIGNMENT.START
        hello.textAlignment = TextUI.ALIGNMENT.CENTER
        hello.dimens.x = 1F
        hello.pixelation = 2F
        hello.text = "Please, enjoy this fine animation:\n"
        hello.material = Color(0xFFFF00FFU)
        container.add(hello)

        val bee = ClipUI()
        bee.anchor = Vector(0.5F, 0.5F)
        bee.texture = TextureCache.getAtlas<Int>("bee")
        bee.pixelation = 8F
        bee.play(20, true, 7, 8, 9, 10)
        container.add(bee)

        root.add(container)

        /*
        val bee1 = ClipUI("bee", Vector(0.55F, 0.4F), 0.3F, 0.5F)
        bee1.play(20, true, 7, 8, 9, 10)
        frame.add(bee1)
        */
    }

    fun end () {
        println("Game ended!")
    }
}
