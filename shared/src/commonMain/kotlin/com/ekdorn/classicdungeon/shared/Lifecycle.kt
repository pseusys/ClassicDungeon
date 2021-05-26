package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.utils.Event



object Lifecycle {
    internal val onUpdate = Event<Unit>()
    internal val onPause = Event<Unit>()
    internal val onResume = Event<Unit>()

    fun update (): Unit = onUpdate.fire(Unit)
    fun pause (): Unit = onPause.fire(Unit)
    fun resume (): Unit = onResume.fire(Unit)
}
