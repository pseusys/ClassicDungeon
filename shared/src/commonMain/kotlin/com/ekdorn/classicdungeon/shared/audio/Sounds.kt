package com.ekdorn.classicdungeon.shared.audio

expect class Sounds () {
    fun add (track: String, value: Any)

    fun play (track: String)

    fun destroy ()
}
