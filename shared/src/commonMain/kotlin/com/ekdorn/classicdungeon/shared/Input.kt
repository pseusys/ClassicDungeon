package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.utils.Event


object Input {
    internal val onBackPressed = Event<Unit>()
    internal val onTouchedUp = Event<Vector>()
    internal val onTouchedDown = Event<Vector>()
    internal val onMoved = Event<Vector>()
    internal val onZoomed = Event<Vector>()
    internal val onResized = Event<Vector>()

    fun onBackPressed () = onBackPressed.fire(Unit)
    fun onPointerDown (x: Int, y: Int) = onTouchedUp.fire(Vector(x, y))
    fun onPointerUp (x: Int, y: Int) = onTouchedDown.fire(Vector(x, y))
    fun onPointerMoved (x: Int, y: Int) = onMoved.fire(Vector(x, y))
    fun onZoomed (x: Int, y: Int) = onZoomed.fire(Vector(x, y))
    fun onResized (w: Int, h: Int) = onResized.fire(Vector(w, h))
}
