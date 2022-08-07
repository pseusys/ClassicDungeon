package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * ResizableUI - widget that can be in some way resized.
 */
@Serializable
internal abstract class ResizableUI: WidgetUI() {
    @Transient protected open var stretchW = true
    @Transient protected open var stretchH = true


    /**
     * Dimens property set made public.
     */
    final override var dimens = Vector()
        public set


    /**
     * Translate method triggered each time dimens AND / OR parentMetrics changed.
     */
    override fun translate() {
        val newMetrics = parentMetrics!! * dimens
        dirty = dirty || (stretchW && (newMetrics.x != metrics.x)) || (stretchH && (newMetrics.y != metrics.y))
        metrics = newMetrics
        super.translate()
    }
}
