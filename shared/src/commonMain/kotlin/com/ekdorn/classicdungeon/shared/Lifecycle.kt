package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.utils.Event
import kotlinx.coroutines.launch


// TODO: runBlocking() in init() and kill() when possible (https://youtrack.jetbrains.com/issue/KT-29403)

object Lifecycle {
    internal val onInit = Event<Unit>()
    internal val onStart = Event<Unit>()
    internal val onResume = Event<Unit>()
    internal val onUpdate = Event<Unit>()
    internal val onPause = Event<Unit>()
    internal val onEnd = Event<Unit>()
    internal val onKill = Event<Unit>()

    /**
     * Initializing features (in MAIN thread), needed for application to start (e.g. display metrics, GL surface, UI textures, etc.)
     */
    suspend fun init (): Unit = onInit.fire(Unit)

    /**
     * Initializing features (in BACK thread) that should be initialized, but not necessarily in main thread (e.g. connection to server, game textures, game soundtrack, etc.)
     */
    fun start () {
        Game.scope.launch { onStart.fire(Unit) }.start()
    }

    /**
     * Resuming game process (e.g. sound, time measuring, etc.)
     */
    fun resume () {
        Game.scope.launch { onResume.fire(Unit) }.start()
    }

    /**
     * Screen update, runs each frame
     */
    fun update () {
        Game.scope.launch { onUpdate.fire(Unit) }.start()
    }

    /**
     * Pausing game processes (e.g. sound, time measuring, etc.)
     */
    fun pause () {
        Game.scope.launch { onPause.fire(Unit) }.start()
    }

    /**
     * Ceasing game functionality (without ability to resume, in MAIN thread) (e.g. freeing resources, auto-saving, closing server connection, etc.)
     */
    suspend fun end (): Unit = onEnd.fire(Unit)
}
