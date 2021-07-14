package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.generics.TextureCache
import com.ekdorn.classicdungeon.shared.engine.glextensions.Atlas
import com.ekdorn.classicdungeon.shared.engine.lib.Listener


// FINAL
internal class ClipUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ImageUI(initializer) {
    private class Animation (fps: Int, val looped: Boolean, val order: IntArray) { val delay = 1000 / fps }



    @Implicit
    private var finished = false
    @Implicit
    private var cut = 0
    @Implicit
    private var timer = 0
    @Implicit
    private var current: Animation? = null

    @Implicit
    var finishedListener: Listener? = null

    var paused = false


    init {
        texture = TextureCache.getAtlas<Int>(initializer.getOrElse("resource") { TextureCache.NO_TEXTURE } as String)
        paused = initializer.getOrElse("paused") { paused } as Boolean
    }



    override fun update (elapsed: Int) {
        super.update(elapsed)
        if (!paused && !finished) current?.let {
            val lastCut = cut
            timer += elapsed
            val prognosis = timer / it.delay
            if (prognosis >= it.order.size) {
                if (it.looped) {
                    cut = 0
                    timer = 0
                } else {
                    finished = true
                    finishedListener?.invoke()
                }
            } else cut = prognosis
            if (lastCut != cut) frame = (texture as Atlas<*>)[cut]!!
        }
    }


    fun play (fps: Int, looped: Boolean = false, vararg order: Int) {
        current = Animation(fps, looped, order)
        cut = 0
        timer = 0
        finished = false
        frame = (texture as Atlas<*>)[cut]!!
    }
}
