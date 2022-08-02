package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.cache.Audio
import com.ekdorn.classicdungeon.shared.engine.ui.extensions.Clickable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
@SerialName("ButtonUI")
internal class ButtonUI: LayoutUI(), Clickable {
    @Transient override var primaryClicked = false
    @Transient override var secondaryClicked = false


    override fun specialChildren() = mapOf("image" to ImageUI::class, "text" to TextUI::class)

    inline var image: ImageUI?
        get() = children["image"] as ImageUI?
        set(v) = if (v != null) add("image", v as WidgetUI) else Unit

    inline var text: TextUI?
        get() = children["text"] as TextUI?
        set(v) = if (v != null) add("text", v as WidgetUI) else Unit


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
}
