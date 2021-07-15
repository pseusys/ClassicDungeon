package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.maths.Vector

// FINAL
internal abstract class ResizableUI (initializer: Map<String, *>): WidgetUI(initializer) {
    @Implicit protected var stretchX = true
    @Implicit protected var stretchY = true

    final override var dimens = super.dimens
        public set

    init {
        dimens = initializer.getOrElse("dimens") { dimens } as Vector
    }


    // Triggered when dimens AND / OR parentMetrics changed.
    override fun translate (parentCoords: Vector, parentMetrics: Vector) {
        val newMetrics = parentMetrics * dimens
        dirty = (stretchX && (newMetrics.x != metrics.x)) || (stretchY && (newMetrics != metrics))
        metrics = newMetrics
        super.translate(parentCoords, parentMetrics)
    }
}
