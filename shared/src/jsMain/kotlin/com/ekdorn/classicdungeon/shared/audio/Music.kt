package com.ekdorn.classicdungeon.shared.audio

import com.ekdorn.classicdungeon.shared.engine.utils.Listener
import org.w3c.dom.Audio


actual class Music actual constructor (private val tracks: Map<String, String>) {
    private var player = Audio().apply {
        autoplay = true
        preload = "auto"
        onended = { finished?.invoke() }
    }

    actual var current: String? = null
    actual var finished: Listener? = null


    actual fun play (track: String, looped: Boolean) {
        player.apply {
            if (track != current) {
                src = tracks[track]!!
                load()
                loop = looped
            }
            play()
        }
        current = track
    }

    actual fun pause () = player.pause()

    actual fun stop() {
        player.apply {
            pause()
            loop = false
        }
        current = null
    }

    actual fun destroy () {
        player.pause()
        player.removeAttribute("src")
        player.load()
    }
}