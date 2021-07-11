package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.utils.Animation
import com.ekdorn.classicdungeon.shared.glextensions.Atlas


internal typealias Listener = () -> Unit

internal class ClipUI (resource: String, pos: Vector, width: Float = -1F, height: Float = -1F): ImageUI(resource, pos, width, height) {
    var paused = false
    var finishedListener: Listener? = null

    private var finished = false
    private var frameNum = 0
    private var timer = 0
    private var current: Animation? = null

    override fun update (elapsed: Int) {
        super.update(elapsed)
        if (!paused && !finished) current?.let {
            val lastFrame = frameNum
            timer += elapsed
            val prognosis = timer / it.delay
            if (prognosis >= it.order.size) {
                if (it.looped) {
                    frameNum = 0
                    timer = 0
                } else {
                    finished = true
                    finishedListener?.invoke()
                }
            } else frameNum = prognosis
            if (lastFrame != frameNum) frame((texture as Atlas<*>)[frameNum]!!)
        }
    }

    fun play (animation: Animation) {
        current = animation
        frameNum = 0
        timer = 0
        finished = false
        frame((texture as Atlas<*>)[frameNum]!!)
    }
}
