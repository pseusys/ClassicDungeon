package com.ekdorn.classicdungeon.shared.engine.general

import com.ekdorn.classicdungeon.shared.IO
import com.ekdorn.classicdungeon.shared.engine.cache.Layout
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.cache.Audio
import com.ekdorn.classicdungeon.shared.engine.ui.ButtonUI
import com.ekdorn.classicdungeon.shared.engine.ui.ClipUI
import com.ekdorn.classicdungeon.shared.engine.ui.ImageUI
import com.ekdorn.classicdungeon.shared.engine.ui.RootUI
import kotlinx.datetime.Clock


// TODO: solve threadlocal
internal object Game {
    private var elapsed = 0

    private lateinit var root: RootUI
    private const val splash = "splash"

    fun resume () {}
    fun update () {
        val current = Clock.System.now().toEpochMilliseconds().toInt()
        root.enter(current - elapsed)
        elapsed = current
    }
    fun pause () {}


    fun start (width: Int, height: Int) {
        println("Game started!")
        root = RootUI(width, height)
        root.add(splash, ImageUI().apply { anchor = Vector(0.5F, 0.5F); pixelation = 8F })

        IO.resizeEvents.add { root.resize(it.w, it.h) }
    }

    fun launch () {
        println("Game launched!")
        root.get<ImageUI>(splash)!!.delete()
        root.remove(splash)

        root.add("menu", Layout.summon("main_menu"))
        root.get<ClipUI>("bee")?.play(20, true, 7, 8, 9, 10)

        elapsed = Clock.System.now().toEpochMilliseconds().toInt()
    }

    fun end () {
        println("Game ended!")
    }
}
