package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions
import com.ekdorn.classicdungeon.shared.engine.Game
import com.ekdorn.classicdungeon.shared.engine.general.Assigned
import com.ekdorn.classicdungeon.shared.engine.general.TextureCache
import com.ekdorn.classicdungeon.shared.engine.general.Transcender
import com.ekdorn.classicdungeon.shared.engine.utils.Event
import kotlinx.coroutines.*


object Lifecycle {
    internal val onResume = Event<Unit>()
    internal val onPause = Event<Unit>()

    val scope = CoroutineScope(Dispatchers.Default)

    /**
     * Initializing features and loading resources.
     * Should not block UI thread, but run in parallel after game started.
     * After this method exits, main game lifecycle should start if no errors occurred.
     * If method finishes with exception, error method should be displayed.
     * @throws com.ekdorn.classicdungeon.shared.engine.ResourceNotFoundException if any resources are not loaded.
     */
    suspend fun start (width: Int, height: Int) {
        Input.onResized.add {
            GLFunctions.portal(it.w, it.h)
            false
        }

        GLFunctions.setup()
        Assigned.assigned.forEach { it.gameStarted() }
        Input.onResized(width, height)

        TextureCache.init("notex")
        Game.splash(width, height)
        Game.update()

        awaitAll(scope.async { delay(2000) }, scope.async {
            TextureCache.load("font", "chrome", "arcs00", "arcs01")
            TextureCache.loadAtlas("bee", List(16) { it }, 16)
            Transcender.load("main_menu")
        })

        Game.start()
    }

    /**
     * Resuming game process in UI thread.
     */
    fun resume () {
        Game.resume()
        onResume.fire(Unit)
    }

    /**
     * Screen update, runs each frame in GL main thread.
     */
    fun update () {
        Game.update()
    }

    /**
     * Pausing game processes in UI thread.
     */
    fun pause () {
        Game.pause()
        onPause.fire(Unit)
    }

    /**
     * Ceasing game functionality without ability to resume, should be executed in UI thread.
     * Preparing game to finish, like auto-saving and closing server connection, should be done in this method too.     */
    fun end () {
        Game.end()
        Assigned.assigned.forEach { it.gameEnded() }
    }
}
