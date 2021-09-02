package com.ekdorn.classicdungeon.shared.audio

import org.w3c.dom.Audio


actual class Sounds {
    private val holder = mutableMapOf<String, Audio>()

    actual fun add (track: String, value: Any) {
        holder[track] = value.unsafeCast<Audio>()
    }

    actual fun play (track: String) {
        holder[track]!!.play()
    }

    actual fun destroy () = holder.clear()
}
