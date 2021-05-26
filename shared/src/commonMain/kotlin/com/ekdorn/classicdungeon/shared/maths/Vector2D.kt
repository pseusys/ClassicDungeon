package com.ekdorn.classicdungeon.shared.maths



import kotlin.math.sqrt

class Vector2D (var x: Double = 0.0, var y: Double = 0.0) {
    operator fun rangeTo (other: Vector2D) = Vector2D(x - other.x, y - other.y)


    fun length () =  sqrt(x * x + y * y)
}
