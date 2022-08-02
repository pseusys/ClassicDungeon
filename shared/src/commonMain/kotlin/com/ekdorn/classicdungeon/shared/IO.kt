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


    internal val logger = Logger.withTag("ClassicDungeon")

    internal val interactiveEvents = EventStack<Event>()
    internal val resizeEvents = EventStack<Vector>()

    private fun mode(mode: Boolean) = if (mode) ClickEvent.ClickMode.PRIMARY else ClickEvent.ClickMode.SECONDARY

    fun onBackPressed () = interactiveEvents.fire(BackEvent())
    fun onClickDown (x: Int, y: Int, primary: Boolean) = interactiveEvents.fire(ClickEvent(ClickType.DOWN, mode(primary), Vector(x, y)))
    fun onClickUp (x: Int, y: Int, primary: Boolean) = interactiveEvents.fire(ClickEvent(ClickType.UP, mode(primary), Vector(x, y)))
    fun onPointerMoved (startX: Int, startY: Int, endX: Int, endY: Int, primary: Boolean) = interactiveEvents.fire(MoveEvent(mode(primary), Vector(startX, startY), Vector(endX, endY)))
    fun onZoomed (x: Int, y: Int, level: Int) = interactiveEvents.fire(ZoomEvent(Vector(x, y), level))
    fun onResized (w: Int, h: Int) = resizeEvents.fire(Vector(w, h))
}
