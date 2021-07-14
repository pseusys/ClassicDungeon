package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.glextensions.Atlas
import com.ekdorn.classicdungeon.shared.lib.Listener


// FINAL
internal class ClipUI: ImageUI() {
    private class Animation (fps: Int, val looped: Boolean, val order: IntArray) { val delay = 1000 / fps }

    var paused = false
    var finishedListener: Listener? = null

    private var finished = false
    private var cut = 0
    private var timer = 0
    private var current: Animation? = null

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
