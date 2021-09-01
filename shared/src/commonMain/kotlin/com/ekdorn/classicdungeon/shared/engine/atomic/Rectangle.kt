package com.ekdorn.classicdungeon.shared.engine.atomic

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.math.abs


/**
 * Class, representing 2D rectangle.
 * Contains two points - upper left and lower right.
 * TODO: revise width + height = right + bottom.
 */
@Serializable
internal data class Rectangle (var left: Float, var top: Float, var right: Float, var bottom: Float) {
    companion object {
        fun create (json: String?, default: Rectangle) = if (json != null) Json.decodeFromString(json) else default
    }

    constructor (left: Int, top: Int, right: Int, bottom: Int): this(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    constructor (): this(0, 1, 1, 0)

    /**
     * Width of rectangle.
     */
    inline var width: Float
        get () = abs(left - right)
        set (v) { right = left + if (right > left) v else -v }

    /**
     * Height of rectangle.
     */
    inline var height: Float
        get () = abs(top - bottom)
        set (v) { bottom = top + if (bottom > top) v else -v }


    /**
     * Coords of rectangle - its upper left corner.
     */
    inline var coords: Vector
        get () = Vector(left, top)
        set (v) { left = v.x; top = v.y }

    /**
     * Metrics of rectangle - its width and height.
     */
    inline var metrics: Vector
        get () = Vector(width, height)
        set (v) { width = v.x; height = v.y }


    /**
     * Horizontal coords of rectangle - left and right.
     */
    inline var horizontal: Vector
        get () = Vector(left, right)
        set (v) { left = v.x; right = v.y }

    /**
     * Vertical coords of rectangle - top and bottom.
     */
    inline var vertical: Vector
        get () = Vector(top, bottom)
        set (v) { top = v.x; bottom = v.y }


    /**
     * Ratio of rectangle - its width divided by its height.
     */
    inline val ratio: Float
        get () = width / height


    /**
     * Rectangle + Vector operations.
     */
    operator fun plus (oper: Vector) = Rectangle(left + oper.x, top + oper.y, right + oper.x, bottom + oper.y)
    operator fun minus (oper: Vector) = Rectangle(left - oper.x, top - oper.y, right - oper.x, bottom - oper.y)
    operator fun times (oper: Vector) = Rectangle(left * oper.x, top * oper.y, right * oper.x, bottom * oper.y)
    operator fun div (oper: Vector) = Rectangle(left / oper.x, top / oper.y, right / oper.x, bottom / oper.y)



    /**
     * Function for translating rectangle: moving it by x and y.
     * @param x x translation
     * @param y y translation
     */
    fun translate (x: Float = 0F, y: Float = 0F) = apply {
        left += x
        right += x
        top += y
        bottom += y
    }

    /**
     * Cast rectangle to array of float coordinates, beginning from its upper left corner clockwise:
     * ┌───────┐
     * │ 0 → 1 │
     * │     ↓ │
     * │ 3 ← 2 │
     * └───────┘
     */
    fun toPointsArray () = floatArrayOf(left, top, right, top, right, bottom, left, bottom)

    override fun toString() =  "Rectangle: (left: $left; top: $top; right: $right; bottom: $bottom)"
}
