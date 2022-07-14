package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.cache.Image
import com.ekdorn.classicdungeon.shared.gl.extensions.Atlas
import com.ekdorn.classicdungeon.shared.engine.utils.Listener
import com.ekdorn.classicdungeon.shared.gl.extensions.ImageTexture
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


/**
 * ClipUI - Image, changing with updates, animation.
 */
@Serializable
internal class ClipUI: ImageUI() {
    /**
     * Animation, sequence of frames to display in ClipUI.
     * - looped - whether animation is looped or fired once.
     * - order - order of frames in atlas to display.
     * - delay - updates between frame changes.
     * - finished - whether animation has ended or still running.
     * TODO: check if needed.
     * - cut - current frame.
     * - timer - time of this animation running.
     * - sus - animation to run when this animation finishes.
     */
    private class Animation (fps: Int, val looped: Boolean, val order: IntArray) {
        val delay = 1000 / fps
        var finished = false
        var cut = 0
        var timer = 0
        var sus: Animation? = null
    }


    /**
     * Property paused - whether the animation is running or paused.
     * False by default.
     */
    var paused = false


    /**
     * Current running animation.
     */
    @Transient private var current: Animation? = null

    /**
     * Listener fired when the animation finishes.
     */
    @Transient var finishedListener: Listener? = null

    @Transient override var texture = Image.getAtlas<Int>(source) as ImageTexture


    /**
     * Get frame from texture for current animation cut.
     * @param animation current animation
     * @return rectangle for current cut
     */
    private fun cut (animation: Animation) = (texture as Atlas<*>)[animation.cut]!!

    override fun update (elapsed: Int) {
        super.update(elapsed)
        if (!paused) current?.let { if (!it.finished) {
            val lastCut = it.cut
            // Adding elapsed time to current animation timer.
            it.timer += elapsed
            // Proposing new cut judging by current animation delay.
            val prognosis = it.timer / it.delay
            if (prognosis >= it.order.size) {
                // Continue from beginning if looped.
                if (it.looped) { it.cut = 0; it.timer = 0 }
                else {
                    it.finished = true
                    // If current animation has suspended animation, suspended becomes current.
                    if (it.sus != null) current = it.sus
                    // Current animation finishes and finished listener fires.
                    else finishedListener?.invoke()
                }
            } else it.cut = prognosis
            if (lastCut != it.cut) frame = cut(current!!)
        } }
    }

    /**
     * Replace current animation.
     * @param fps frames per second to play
     * @param looped whether this animation is looped or not
     * @param order frame order to play
     */
    fun play (fps: Int, looped: Boolean = false, vararg order: Int) {
        current = Animation(fps, looped, order)
        frame = cut(current!!)
    }

    /**
     * Same as play, but current animation gets preserved and resumed after this animation finishes.
     * @param fps frames per second to play
     * @param order frame order to play
     */
    fun suspend (fps: Int, vararg order: Int) {
        if (current != null) current!!.sus = current
        play(fps, false, *order)
    }
}
