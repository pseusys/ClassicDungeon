package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.cache.Audio
import com.ekdorn.classicdungeon.shared.engine.ui.implementable.Interactive
import com.ekdorn.classicdungeon.shared.engine.ui.implementable.resetColor
import com.ekdorn.classicdungeon.shared.engine.ui.implementable.setBrightness
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("ButtonUI")
internal class ButtonUI: LayoutUI(), Interactive {
    override var clickTime: Int? = null
    override var clickable = true

    // TODO: var image: ImageUI? by this
    var image: ImageUI? = null
        set (v) {
            field?.parent = null
            v?.parent = this
            field = v
        }

    // TODO: var image: ImageUI? by this
    var text: TextUI? = null
        set (v) {
            field?.parent = null
            v?.parent = this
            field = v
        }

    init {
        text?.parent = this
        image?.parent = this
    }


    override fun update(elapsed: Int) {
        super.update(elapsed)
        if (image != null && image!!.visible) image!!.update(elapsed)
        if (text != null && text!!.visible) text!!.update(elapsed)
        updateCallback()
    }

    override fun draw() {
        super.draw()
        if (image != null && image!!.visible) image!!.draw()
        if (text != null && text!!.visible) text!!.draw()
    }

    override fun translateInnerChildren(parentCoords: Vector, parentMetrics: Vector) {
        super.translateInnerChildren(parentCoords, parentMetrics)
        if (image != null && image!!.visible) image!!.translate(parentCoords, parentMetrics)
        if (text != null && text!!.visible) text!!.translate(parentCoords, parentMetrics)
    }

    override fun delete() {
        super.delete()
        if (image != null) image!!.delete()
        if (text != null) text!!.delete()
    }


    override fun onClick() {
        if (!Audio.backgroundPlaying) {
            Audio.playBackground("theme", true)
            text?.text = "Click for music to stop!" // FIXME
        } else {
            Audio.stopBackground()
            text?.text = "Click for music to start!"
        }
    }

    override fun onTouchUp(pos: Vector): Boolean {
        background?.resetColor()
        return super.onTouchUp(pos)
    }

    override fun onTouchDown(pos: Vector): Boolean {
        println("touchdown!")
        background?.setBrightness(1.2F)
        Audio.playEffect("snd_click")
        return super.onTouchDown(pos)
    }
}
