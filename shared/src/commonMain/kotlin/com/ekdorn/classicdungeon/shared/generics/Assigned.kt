package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.Game
import com.ekdorn.classicdungeon.shared.glwrapper.Script

internal interface Assigned {
    fun gameStarted ()
    fun gameEnded ()

    companion object AllSet {
        val assigned = listOf(Game, Script)
    }
}
