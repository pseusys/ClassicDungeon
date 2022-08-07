package com.ekdorn.classicdungeon.shared.engine.ui.extensions

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.Transient


internal interface Zoomable {
    @Transient val zoomable: Boolean
        get() = true

    fun onZoom (pos: Vector, level: Int) = zoomable
}
