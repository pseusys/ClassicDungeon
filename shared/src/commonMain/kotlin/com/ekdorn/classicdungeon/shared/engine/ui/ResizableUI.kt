package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.maths.Vector

// FINAL
internal abstract class ResizableUI (initializer: Map<String, *>): WidgetUI(initializer) {
    final override var dimens = super.dimens
        public set

    init {
        dimens = initializer.getOrElse("dimens") { dimens } as Vector
    }


    override fun translate (parentCoords: Vector, parentMetrics: Vector) {
        val newMetrics = parentMetrics * dimens
        if (newMetrics != metrics) resize(newMetrics)
        metrics = newMetrics
        super.translate(parentCoords, parentMetrics)
    }

    open fun resize (newMetrics: Vector) = updateVertices()
}
