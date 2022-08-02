package com.ekdorn.classicdungeon.shared

import co.touchlab.kermit.Logger
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.utils.*
import com.ekdorn.classicdungeon.shared.engine.utils.BackEvent
import com.ekdorn.classicdungeon.shared.engine.utils.ClickEvent
import com.ekdorn.classicdungeon.shared.engine.utils.ClickEvent.ClickType
import com.ekdorn.classicdungeon.shared.engine.utils.EventStack
import com.ekdorn.classicdungeon.shared.engine.utils.MoveEvent


object IO {
    const val TOUCHSCREEN_SECONDARY_DELAY = 500

    enum class ClickMode {
        PRIMARY, SECONDARY, BOTH;

        internal inline val mode: ClickEvent.ClickMode
            get() = when (this) {
                PRIMARY -> ClickEvent.ClickMode.PRIMARY
                SECONDARY -> ClickEvent.ClickMode.SECONDARY
                BOTH -> ClickEvent.ClickMode.BOTH
            }
    }


    internal val logger = Logger.withTag("ClassicDungeon")

    internal val interactiveEvents = EventStack<Event>()
    internal val resizeEvents = EventStack<Vector>()


    fun onBackPressed () = interactiveEvents.fire(BackEvent())
    fun onClickDown (x: Int, y: Int, mode: ClickMode) = interactiveEvents.fire(ClickEvent(ClickType.DOWN, Vector(x, y), mode.mode))
    fun onClickUp (x: Int, y: Int, mode: ClickMode) = interactiveEvents.fire(ClickEvent(ClickType.UP, Vector(x, y), mode.mode))
    fun onPointerMoved (startX: Int, startY: Int, endX: Int, endY: Int) = interactiveEvents.fire(MoveEvent(Vector(startX, startY), Vector(endX, endY)))
    fun onZoomed (x: Int, y: Int, level: Int) = interactiveEvents.fire(ZoomEvent(Vector(x, y), level))
    fun onResized (w: Int, h: Int) = resizeEvents.fire(Vector(w, h))
}
