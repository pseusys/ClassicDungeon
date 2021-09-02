package com.ekdorn.classicdungeon.shared.audio

import com.ekdorn.classicdungeon.shared.engine.utils.Listener


expect class Music (tracks: Map<String, String>) {
    var current: String?
    var finished: Listener?

    fun play (track: String, looped: Boolean)

    fun pause ()

    fun stop ()

    fun destroy ()
}
