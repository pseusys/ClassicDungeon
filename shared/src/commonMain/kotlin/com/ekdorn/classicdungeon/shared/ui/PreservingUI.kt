package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

// TODO: maybe combine with element.ui
internal abstract class PreservingUI private constructor (rect: Rectangle): ElementUI(rect) {
    constructor (pos: Vector, width: Float, height: Float): this(Rectangle(pos.x, pos.y, width, height)) {
        if ((width == -1F) && (height == -1F)) metrics.apply { x = 1F; y = 1F }
        else {
            floatingWidth = width == -1F
            floatingHeight = height == -1F
        }
    }

    var preserving = true

    protected var floatingWidth = false
    protected var floatingHeight = false


    override var parent: LayoutUI? = null
        set (value) {
            if (value != null) parentalResize(value.pixelMetrics.w, value.pixelMetrics.h)
            else {
                if (floatingWidth) metrics.x = -1F
                if (floatingHeight) metrics.y = -1F
            }
            field = value
        }

    abstract fun parentalResize (pixelWidth: Int, pixelHeight: Int)
}
