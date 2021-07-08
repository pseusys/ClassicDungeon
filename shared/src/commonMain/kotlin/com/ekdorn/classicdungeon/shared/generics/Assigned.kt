package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.Game
import com.ekdorn.classicdungeon.shared.glextensions.Camera
import com.ekdorn.classicdungeon.shared.glextensions.Script

/**
 * This interface represents **object**, assigned to app lifecycle, to _start_ and _end_ events in particular.
 */
internal interface Assigned {
    /**
     * Function, called once the app started. Following actions advised for implementation:
     * - Initial configuration
     * - Setting listeners for events
     */
    fun gameStarted () {}

    /**
     * Function, called once the app is about to finish. Following actions advised for implementation:
     * - Releasing resources
     */
    fun gameEnded () {}

    /**
     * Set of all Assigned implementations, used for function propagation.
     */
    companion object AllSet {
        val assigned = listOf(Script, TextureCache, Camera)
    }
}
