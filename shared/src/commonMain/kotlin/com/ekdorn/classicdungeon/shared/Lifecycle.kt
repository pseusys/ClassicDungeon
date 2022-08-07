package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.engine.cache.Audio
import com.ekdorn.classicdungeon.shared.engine.cache.Image
import com.ekdorn.classicdungeon.shared.engine.cache.Layout
import com.ekdorn.classicdungeon.shared.engine.utils.Assigned
import com.ekdorn.classicdungeon.shared.engine.cache.ResourceLists
import com.ekdorn.classicdungeon.shared.engine.cache.ResourceNotFoundException
import com.ekdorn.classicdungeon.shared.engine.utils.EventStack
import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object Lifecycle {
    internal val onResume = EventStack<Unit>()
    internal val onPause = EventStack<Unit>()

    val scope = CoroutineScope(Dispatchers.Default)

    suspend fun init(width: Int, height: Int) {
        IO.resizeEvents.add { GLFunctions.portal(it.w, it.h) }

        GLFunctions.setup()
        Assigned.assigned.forEach { it.gameStarted() }
        IO.onResized(width, height)

        Image.init(ResourceLists.splash_textures)
        Game.init(width, height)
    }

    /**
     * Initializing features and loading resources.
     * Should not block UI thread, but run in parallel after game started.
     * After this method exits, main game lifecycle should start if no errors occurred.
     * If method finishes with exception, error method should be displayed.
     * @throws ResourceNotFoundException if any resources are not loaded.
     */
    suspend fun start () {
        // TODO: refactor splash screen, replace with loading progress bar that will switch to play button
        withContext(scope.coroutineContext) {
            Audio.loadEffect("snd_click")
            Image.load("font", "chrome", "arcs00", "arcs01")
            Image.loadAtlas("bee", List(16) { it }, 16)
            Layout.load("main_menu")
        }
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
