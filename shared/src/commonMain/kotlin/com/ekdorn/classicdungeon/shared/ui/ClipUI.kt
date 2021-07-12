package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.glextensions.Atlas


internal typealias Listener = () -> Unit

internal class ClipUI (resource: String, pos: Vector, width: Float = -1F, height: Float = -1F): ImageUI(resource, pos, width, height) {
    private class Animation (fps: Int, val looped: Boolean, val order: IntArray) {
        val delay = 1000 / fps
    }

    var paused = false
    var finishedListener: Listener? = null

    private var finished = false
    private var frame = 0
    private var timer = 0
    private var current: Animation? = null

    override fun update (elapsed: Int) {
        super.update(elapsed)
        if (!paused && !finished) current?.let {
            val lastFrame = frame
            timer += elapsed
            val prognosis = timer / it.delay
            if (prognosis >= it.order.size) {
                if (it.looped) {
                    frame = 0
                    timer = 0
                } else {
                    finished = true
                    finishedListener?.invoke()
                }
            } else frame = prognosis
            if (lastFrame != frame) frame((texture as Atlas<*>)[frame]!!)
        }
    }

    fun play (fps: Int, looped: Boolean = false, vararg order: Int) {
        current = Animation(fps, looped, order)
        frame = 0
        timer = 0
        finished = false
        frame((texture as Atlas<*>)[frame]!!)
    }
}
