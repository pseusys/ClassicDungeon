package com.ekdorn.classicdungeon.shared.engine.ui.implementable

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.Transient


internal interface Zoomable {
    @Transient val zoomable: Boolean

    fun onZoom (pos: Vector, level: Int) = zoomable
}
