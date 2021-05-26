package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.maths.Vector2D
import com.ekdorn.classicdungeon.shared.utils.Event



object Input {
    internal val onBackPressed = Event<Unit>()
    internal val onTouchedUp = Event<Vector2D>()
    internal val onTouchedDown = Event<Vector2D>()
    internal val onMoved = Event<Vector2D>()
    internal val onZoomed = Event<Vector2D>()

    fun onBackPressed (): Unit = onBackPressed.fire(Unit)
    fun onPointerDown (x: Int, y: Int): Unit = onTouchedUp.fire(Vector2D(x.toDouble(), y.toDouble()))
    fun onPointerUp (x: Int, y: Int): Unit = onTouchedDown.fire(Vector2D(x.toDouble(), y.toDouble()))
    fun onPointerMoved (x: Int, y: Int): Unit = onMoved.fire(Vector2D(x.toDouble(), y.toDouble()))
    fun onZoomed (x: Int, y: Int): Unit = onZoomed.fire(Vector2D(x.toDouble(), y.toDouble()))
}
