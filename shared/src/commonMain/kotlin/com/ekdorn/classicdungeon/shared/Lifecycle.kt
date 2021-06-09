package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.utils.Event
import kotlinx.coroutines.launch


// TODO: runBlocking() in init() and kill() when possible (https://youtrack.jetbrains.com/issue/KT-29403)

object Lifecycle {
    internal val onInit = Event<Unit>()
    internal val onStart = Event<Unit>()
    internal val onUpdate = Event<Unit>()
    internal val onPause = Event<Unit>()
    internal val onResume = Event<Unit>()
    internal val onEnd = Event<Unit>()
    internal val onKill = Event<Unit>()

    suspend fun init (): Unit = onInit.fire(Unit)
    fun start (): Boolean = Game.scope.launch { onStart.fire(Unit) }.start()
    fun resume (): Boolean = Game.scope.launch { onResume.fire(Unit) }.start()
    fun update (): Boolean = Game.scope.launch { onUpdate.fire(Unit) }.start()
    fun pause (): Boolean = Game.scope.launch { onPause.fire(Unit) }.start()
    fun end (): Boolean = Game.scope.launch { onEnd.fire(Unit) }.start()
    suspend fun kill (): Unit = onKill.fire(Unit)
}
