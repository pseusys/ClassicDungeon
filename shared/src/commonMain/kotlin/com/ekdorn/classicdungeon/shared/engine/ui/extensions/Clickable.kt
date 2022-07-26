package com.ekdorn.classicdungeon.shared.engine.ui.extensions

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.Transient


internal interface Clickable {
    companion object {
        private const val LONG_CLICK = 3.0
    }


    @Transient var clickTime: Int?

    @Transient val clickable: Boolean


    fun onClick () {}

    fun onLongClick () {}


    fun onTouchUp (pos: Vector) = if (clickable) {
        clickTime = null
        true
    } else false

    fun onTouchDown (pos: Vector) = if (clickable) {
        clickTime = 0
        onClick()
        true
    } else false

    fun updateCallback() = if (clickable && clickTime != null) {
        if (clickTime!! > LONG_CLICK) onLongClick()
        clickTime = clickTime!! + 1
    } else Unit
}
