package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.abs

internal data class Rectangle (val left: Double, val top: Double, val right: Double, val bottom: Double) {
    fun width (): Double = abs(left - right)
    fun height (): Double = abs(top - bottom)

    fun toPointsArray (): DoubleArray = doubleArrayOf(left, top, right, top, right, bottom, left, bottom)
}
