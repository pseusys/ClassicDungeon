package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.cache.Audio
import com.ekdorn.classicdungeon.shared.engine.ui.extensions.Clickable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.reflect.KClass


@Serializable
@SerialName("ButtonUI")
internal class ButtonUI: LayoutUI(), Clickable {
    @Transient override var primaryClicked = false
    @Transient override var secondaryClicked = false


    var image: ImageUI? by this
    var text: TextUI? by this


    override fun onPrimaryClickUp(pos: Vector): Boolean {
        background?.resetColor()
        return super.onPrimaryClickUp(pos)
    }

    override fun onPrimaryClickDown(pos: Vector): Boolean {
        background?.setBrightness(1.2F)
        Audio.playEffect("snd_click")
        if (!Audio.backgroundPlaying) {
            Audio.playBackground("theme", true)
            text?.text = "Click for music to stop!"
        } else {
            Audio.stopBackground()
            text?.text = "Click for music to start!"
        }
        return super.onPrimaryClickDown(pos)
    }


    override fun toString() = "${super.toString()} text '${text?.text}', image '${image?.source}'"
}
