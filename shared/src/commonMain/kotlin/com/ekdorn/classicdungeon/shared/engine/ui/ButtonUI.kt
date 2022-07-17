package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.Serializable


@Serializable
internal class ButtonUI: LayoutUI() {
    override var touchable = true

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


    override fun onClick(pos: Vector): Boolean {
        return super.onClick(pos)
    }

    override fun onTouchUp(pos: Vector): Boolean {
        return super.onTouchUp(pos)
    }

    override fun onTouchDown(pos: Vector): Boolean {
        return super.onTouchDown(pos)
    }
}
