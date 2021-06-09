package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.maths.Vector2D
import com.ekdorn.classicdungeon.shared.utils.Event
import kotlinx.coroutines.launch


object Input {
    internal val onBackPressed = Event<Unit>()
    internal val onTouchedUp = Event<Vector2D>()
    internal val onTouchedDown = Event<Vector2D>()
    internal val onMoved = Event<Vector2D>()
    internal val onZoomed = Event<Vector2D>()

    fun onBackPressed (): Boolean = Game.scope.launch { onBackPressed.fire(Unit) }.start()
    fun onPointerDown (x: Int, y: Int): Boolean = Game.scope.launch { onTouchedUp.fire(Vector2D(x.toDouble(), y.toDouble())) }.start()
    fun onPointerUp (x: Int, y: Int): Boolean = Game.scope.launch { onTouchedDown.fire(Vector2D(x.toDouble(), y.toDouble())) }.start()
    fun onPointerMoved (x: Int, y: Int): Boolean = Game.scope.launch { onMoved.fire(Vector2D(x.toDouble(), y.toDouble())) }.start()
    fun onZoomed (x: Int, y: Int): Boolean = Game.scope.launch { onZoomed.fire(Vector2D(x.toDouble(), y.toDouble())) }.start()
}
