package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.generics.TextureCache
import com.ekdorn.classicdungeon.shared.engine.glextensions.Atlas
import com.ekdorn.classicdungeon.shared.engine.lib.Listener


internal class ClipUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ImageUI(initializer) {
    private class Animation (fps: Int, val looped: Boolean, val order: IntArray) {
        val delay = 1000 / fps
        var finished = false
        var cut = 0
        var timer = 0
        var sus: Animation? = null
    }



    @Implicit private var current: Animation? = null

    @Implicit var finishedListener: Listener? = null

    var paused = initializer.getOrElse("paused") { false } as Boolean


    init {
        texture = TextureCache.getAtlas<Int>(initializer.getOrElse("resource") { TextureCache.NO_TEXTURE } as String)
    }



    private fun cut (animation: Animation) = (texture as Atlas<*>)[animation.cut]!!



    override fun update (elapsed: Int) {
        super.update(elapsed)
        if (!paused) current?.let { if (!it.finished) {
            val lastCut = it.cut
            it.timer += elapsed
            val prognosis = it.timer / it.delay
            if (prognosis >= it.order.size) {
                if (it.looped) { it.cut = 0; it.timer = 0 }
                else {
                    it.finished = true
                    if (it.sus != null) current = it.sus
                    else finishedListener?.invoke()
                }
            } else it.cut = prognosis
            if (lastCut != it.cut) frame = cut(current!!)
        } }
    }


    fun play (fps: Int, looped: Boolean = false, vararg order: Int) {
        current = Animation(fps, looped, order)
        frame = cut(current!!)
    }

    fun suspend (fps: Int, vararg order: Int) {
        if (current != null) {
            current!!.sus = current
            current = Animation(fps, false, order)
        } else play(fps, false, *order)
    }
}
