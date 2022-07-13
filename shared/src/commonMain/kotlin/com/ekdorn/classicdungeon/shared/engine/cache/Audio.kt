package com.ekdorn.classicdungeon.shared.engine.cache

import com.ekdorn.classicdungeon.shared.engine.general.Assigned
import com.ekdorn.classicdungeon.shared.engine.general.ResourceLoader
import com.ekdorn.classicdungeon.shared.audio.Music
import com.ekdorn.classicdungeon.shared.audio.Sounds


internal object Audio: Assigned {
    const val DEFAULT = "noson"

    private val background = Music(listOf("theme").distinct().associateWith { "./sounds/$it.mp3" })
    private val effect = Sounds()


    suspend fun loadEffect (vararg values: String) = values.forEach {
        effect.add(it, ResourceLoader.loadSound("sounds/$it.mp3"))
    }


    fun playEffect (track: String) = effect.play(track)


    fun playBackground (track: String, looped: Boolean) = background.play(track, looped)

    fun playBackgroundLoop (from: Set<String>) {
        val repeat = {
            var new: String
            do { new = from.random() }
            while ((from.size > 1) && (new != background.current))
            background.play(new, false)
        }
        background.finished = repeat
        repeat.invoke()
    }

    fun pauseBackgroundLoop () = background.pause()

    fun stopBackgroundLoop () {
        background.stop()
        background.finished = null
    }


    override fun gameEnded() {
        background.destroy()
        effect.destroy()
    }
}
