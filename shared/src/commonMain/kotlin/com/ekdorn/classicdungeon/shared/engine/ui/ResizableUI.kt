package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.maths.Vector

// FINAL
internal abstract class ResizableUI (initializer: Map<String, *>): WidgetUI(initializer) {
    @Implicit protected open var stretchW = true
    @Implicit protected open var stretchH = true

    final override var dimens = initializer.getOrElse("dimens") { super.dimens } as Vector
        public set



    // Triggered when dimens AND / OR parentMetrics changed.
    override fun translate (parentCoords: Vector, parentMetrics: Vector) {
        val newMetrics = parentMetrics * dimens
        dirty = (stretchW && (newMetrics.x != metrics.x)) || (stretchH && (newMetrics != metrics))
        metrics = newMetrics
        super.translate(parentCoords, parentMetrics)
    }
}
