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
    internal val logger = Logger.withTag("ClassicDungeon")

    internal val interactiveEvents = EventStack<Event>()
    internal val resizeEvents = EventStack<Vector>()

    fun onBackPressed () = interactiveEvents.fire(BackEvent())
    fun onPointerDown (x: Int, y: Int) = interactiveEvents.fire(ClickEvent(ClickType.DOWN, Vector(x, y)))
    fun onPointerUp (x: Int, y: Int) = interactiveEvents.fire(ClickEvent(ClickType.UP, Vector(x, y)))
    fun onPointerMoved (startX: Int, startY: Int, endX: Int, endY: Int) = interactiveEvents.fire(MoveEvent(Vector(startX, startY), Vector(endX, endY)))
    fun onZoomed (x: Int, y: Int, level: Int) = interactiveEvents.fire(ZoomEvent(Vector(x, y), level))
    fun onResized (w: Int, h: Int) = resizeEvents.fire(Vector(w, h))
}
