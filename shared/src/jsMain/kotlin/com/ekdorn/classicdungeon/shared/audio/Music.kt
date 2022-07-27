package com.ekdorn.classicdungeon.shared.audio

import com.ekdorn.classicdungeon.shared.engine.general.ResourceNotFoundException
import com.ekdorn.classicdungeon.shared.engine.utils.Listener
import org.w3c.dom.Audio
import kotlin.js.Promise


actual class Music actual constructor (private val tracks: Map<String, String>) {
    private var player = Audio()

    actual var current: String? = null
    actual var finished: Listener? = null


    private fun loadTrack(track: String): Promise<Audio> = Promise { resolve, reject ->
        Audio(tracks[track]!!).apply {
            preload = "auto"
            onended = { finished?.invoke() }
            onerror = { _, _, _, _, _ -> reject(ResourceNotFoundException(track)) }
            oncanplaythrough = { resolve(this) }
        }
    }

    actual fun play (track: String, looped: Boolean) {
        if (track != current) {
            loadTrack(track).then { player = it.apply {
                loop = looped
                play()
            } }
            current = track
        } else player.play()
    }

    actual fun pause () = player.pause()

    actual fun stop() {
        player.apply {
            pause()
            loop = false
        }
        current = null
    }

    actual fun destroy () = stop()
}
