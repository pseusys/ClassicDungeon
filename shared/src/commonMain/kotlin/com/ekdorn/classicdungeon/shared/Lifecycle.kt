package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.utils.Event



object Lifecycle {
    internal val onStart = Event<Unit>()
    internal val onUpdate = Event<Unit>()
    internal val onPause = Event<Unit>()
    internal val onResume = Event<Unit>()
    internal val onEnd = Event<Unit>()

    fun start (): Unit = onStart.fire(Unit)
    fun update (): Unit = onUpdate.fire(Unit)
    fun pause (): Unit = onPause.fire(Unit)
    fun resume (): Unit = onResume.fire(Unit)
    fun end (): Unit = onEnd.fire(Unit)
}
