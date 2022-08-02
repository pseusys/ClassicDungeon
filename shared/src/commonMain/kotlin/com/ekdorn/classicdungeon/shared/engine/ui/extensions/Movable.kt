package com.ekdorn.classicdungeon.shared.engine.ui.extensions

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.utils.ClickEvent
import kotlinx.serialization.Transient


internal interface Movable {
    @Transient val movable: Boolean
        get() = true

    fun onMove (mode: ClickEvent.ClickMode, start: Vector, end: Vector) = movable
}
