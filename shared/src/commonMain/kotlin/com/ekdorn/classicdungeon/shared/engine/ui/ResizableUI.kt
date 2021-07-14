package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.maths.Vector

// FINAL
internal abstract class ResizableUI (initializer: Map<String, *>): WidgetUI(initializer) {
    final override var dimens = super.dimens
        public set

    init {
        dimens = initializer.getOrElse("dimens") { dimens } as Vector
    }


    // WARNING: need t divide by pixelation in order to scale pixels only and not metrics
    override fun translate (parentAnchor: Vector, parentMetrics: Vector) {
        super.translate(parentAnchor, parentMetrics)
        val newMetrics = parentMetrics * dimens / pixelation
        if (newMetrics != metrics) resize(newMetrics)
        metrics = newMetrics
    }

    open fun resize (newMetrics: Vector) = updateVertices()
}
