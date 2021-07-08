package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.generics.Assigned
import com.ekdorn.classicdungeon.shared.generics.TextureCache
import com.ekdorn.classicdungeon.shared.utils.Event
import kotlinx.coroutines.delay


object Lifecycle {
    internal val onResume = Event<Unit>()
    internal val onPause = Event<Unit>()

    /**
     * Initializing features (blocking MAIN thread), needed for application to start (e.g. display metrics, GL surface, UI textures, etc.)
     */
    suspend fun start (width: Int, height: Int) {
        Input.onResized.add {
            GLFunctions.portal(it.w, it.h)
            false
        }

        GLFunctions.setup()
        Assigned.assigned.forEach { it.gameStarted() }
        Input.onResized(width, height)

        TextureCache.load("sample")
        Game.splash(width, height)
        Game.update()
        
        delay(1000)
        // TextureCache.load("sample") load other
        Game.start()
    }

    /**
     * Resuming game process async (e.g. sound, time measuring, etc.)
     */
    fun resume () {
        Game.resume()
        onResume.fire(Unit)
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
        onPause.fire(Unit)
    }

    /**
     * Ceasing game functionality (without ability to resume, in MAIN thread) (e.g. freeing resources, auto-saving, closing server connection, etc.)
     */
    fun end () {
        Game.end()
        Assigned.assigned.forEach { it.gameEnded() }
    }
}
