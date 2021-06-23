package com.ekdorn.classicdungeon.shared

import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.utils.Event
import kotlinx.coroutines.launch


object Input {
    internal val onBackPressed = Event<Unit>()
    internal val onTouchedUp = Event<Vector>()
    internal val onTouchedDown = Event<Vector>()
    internal val onMoved = Event<Vector>()
    internal val onZoomed = Event<Vector>()

    fun onBackPressed () = Game.scope.launch { onBackPressed.fire(Unit) }.start()
    fun onPointerDown (x: Int, y: Int) = Game.scope.launch { onTouchedUp.fire(Vector(x, y)) }.start()
    fun onPointerUp (x: Int, y: Int) = Game.scope.launch { onTouchedDown.fire(Vector(x, y)) }.start()
    fun onPointerMoved (x: Int, y: Int) = Game.scope.launch { onMoved.fire(Vector(x, y)) }.start()
    fun onZoomed (x: Int, y: Int) = Game.scope.launch { onZoomed.fire(Vector(x, y)) }.start()
}
