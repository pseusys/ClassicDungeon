package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.Game
import com.ekdorn.classicdungeon.shared.glwrapper.Script

internal interface Assigned {
    suspend fun gameStarted ()
    suspend fun gameEnded ()

    companion object AllSet {
        val assigned = listOf(Script, TextureCache, Game)
    }
}
