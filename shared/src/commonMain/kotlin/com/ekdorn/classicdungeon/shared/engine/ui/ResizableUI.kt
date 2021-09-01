package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector

/**
 * ResizableUI - widget that can be in some way resized.
 */
internal abstract class ResizableUI (initializer: Map<String, *>): WidgetUI(initializer) {
    @Implicit protected open var stretchW = true
    @Implicit protected open var stretchH = true

    /**
     * Dimens property set made public.
     */
    final override var dimens = Vector.create(initializer["dimens"] as String?, Vector())
        public set


    /**
     * Translate method triggered each time dimens AND / OR parentMetrics changed.
     */
    override fun translate (parentCoords: Vector, parentMetrics: Vector) {
        val newMetrics = parentMetrics * dimens
        dirty = (stretchW && (newMetrics.x != metrics.x)) || (stretchH && (newMetrics.y != metrics.y))
        metrics = newMetrics
        super.translate(parentCoords, parentMetrics)
    }
}
