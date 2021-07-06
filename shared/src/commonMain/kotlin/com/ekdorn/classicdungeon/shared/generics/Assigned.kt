package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.Game
import com.ekdorn.classicdungeon.shared.glwrapper.Camera
import com.ekdorn.classicdungeon.shared.glwrapper.Script

internal interface Assigned {
    suspend fun gameStarted (screenWidth: Int, screenHeight: Int)
    suspend fun gameEnded ()

    companion object AllSet {
        val assigned = listOf(Game, Script, TextureCache, Camera)
    }
}
