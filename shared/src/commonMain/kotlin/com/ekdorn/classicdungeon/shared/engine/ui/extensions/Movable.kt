package com.ekdorn.classicdungeon.shared.engine.ui.extensions

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.Transient


internal interface Movable {
    @Transient val movable: Boolean
        get() = true

    fun onMove (start: Vector, end: Vector) = movable
}
