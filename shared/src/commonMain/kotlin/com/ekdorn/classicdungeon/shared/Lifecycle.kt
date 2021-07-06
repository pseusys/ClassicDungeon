package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.generics.Assigned
import com.ekdorn.classicdungeon.shared.utils.Event
import kotlinx.coroutines.launch


object Lifecycle {
    internal val onResume = Event<Unit>()
    internal val onPause = Event<Unit>()

    /**
     * Initializing features (in MAIN thread), needed for application to start (e.g. display metrics, GL surface, UI textures, etc.)
     */

    suspend fun start (screenWidth: Int, screenHeight: Int) {
        GLFunctions.setup()
        GLFunctions.viewport(screenWidth, screenHeight)
        Input.onResized.add {
            GLFunctions.viewport(it.w, it.h)
            false
        }

        Assigned.assigned.forEach { it.gameStarted(screenWidth, screenHeight) }
        Game.afterStarted(screenWidth, screenHeight)
    }

    /**
     * Resuming game process async (e.g. sound, time measuring, etc.)
     */
    fun resume () {
        Game.resume()
        Game.scope.launch { onResume.fire(Unit) }.start()
    }

    /**
     * Screen update, runs each frame in GL thread sync
     */
    fun update () {
        Game.update()
    }

    /**
     * Pausing game processes async (e.g. sound, time measuring, etc.)
     */
    fun pause () {
        Game.pause()
        Game.scope.launch { onPause.fire(Unit) }.start()
    }

    /**
     * Ceasing game functionality (without ability to resume, in MAIN thread) (e.g. freeing resources, auto-saving, closing server connection, etc.)
     */
    suspend fun end () = Assigned.assigned.forEach { it.gameEnded() }
}
