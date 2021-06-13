package com.ekdorn.classicdungeon.shared.generics

internal sealed interface Assigned {
    fun gameStarted ()
    fun gameEnded ()

    companion object AllSet {
        val assigned = listOf<Assigned>(Game)
    }
}
