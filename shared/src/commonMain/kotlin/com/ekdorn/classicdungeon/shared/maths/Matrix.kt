package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

internal data class Matrix (private val values: FloatArray) {
    constructor (
        i11: Float, i12: Float, i13: Float,
        i21: Float, i22: Float, i23: Float,
        i31: Float, i32: Float, i33: Float
    ): this(floatArrayOf(i11, i12, i13, i21, i22, i23, i31, i32, i33))

    constructor (): this(
        1F, 0F, 0F,
        0F, 1F, 0F,
        0F, 0F, 1F
    )

    fun to4x4 () = floatArrayOf(
        values[0], values[1], values[2], 0F,
        values[3], values[4], values[5], 0F,
        values[6], values[7], values[8], 0F,
        0F, 0F, 0F, 1F
    )


    private fun toRadians (deg: Float) = (deg * PI / 180).toFloat()


    fun toIdentity () = values.forEachIndexed { index, _ -> values[index] = if (index % 4 == 0) 1F else 0F }

    /**
     * ┌                  ┐   ┌       ┐   ┌                                                          ┐
     * │ cos(t)  sin(t) 0 │   │ a b c │   │ a*cos(t)+d*sin(t)  b*cos(t)+e*sin(t)  c*cos(t)+f*sin(t)  │
     * │ -sin(t) cos(t) 0 │ X │ d e f │ = │ -a*sin(t)+d*cos(t) -b*sin(t)+e*cos(t) -c*sin(t)+f*cos(t) │
     * │ 0       0      1 │   │ g h i │   │ g                  h                  i                  │
     * └                  ┘   └       ┘   └                                                          ┘
     */
    fun rotate (degrees: Float) {
        if (degrees == 0F) return
        val radians = toRadians(degrees)
        val sin = sin(radians)
        val cos = cos(radians)
        val mat = values.copyOf()
        for (i in 0 .. 3) {
            values[i] = cos * mat[i] + sin * mat[i + 3]
            values[i + 3] = -sin * mat[i] + cos * mat[i + 3]
        }
    }

    /**
     * ┌            ┐   ┌       ┐   ┌                                  ┐
     * │ 1      0 0 │   │ a b c │   │ a          b          c          │
     * │ tan(t) 1 0 │ X │ d e f │ = │ a*tan(t)+d b*tan(t)+e c*tan(t)+f │
     * │ 0      0 1 │   │ g h i │   │ g          h          i          │
     * └            ┘   └       ┘   └                                  ┘
     */
    fun sheerX (degrees: Float) {
        if (degrees == 0F) return
        val tan = tan(toRadians(degrees))
        for (i in 0 .. 3) values[i + 3] += values[i] * tan
    }

    /**
     * ┌            ┐   ┌       ┐   ┌                                  ┐
     * │ 1 tan(t) 0 │   │ a b c │   │ a+d*tan(t) b+e*tan(t) c+f*tan(t) │
     * │ 0 1      0 │ X │ d e f │ = │ d          e          f          │
     * │ 0 0      1 │   │ g h i │   │ g          h          i          │
     * └            ┘   └       ┘   └                                  ┘
     */
    fun sheerY (degrees: Float) {
        if (degrees == 0F) return
        val tan = tan(toRadians(degrees))
        for (i in 0 .. 3) values[i] += values[i + 3] * tan
    }

    /**
     * ┌       ┐   ┌       ┐   ┌             ┐
     * │ x 0 0 │   │ a b c │   │ a*x b*x c*x │
     * │ 0 y 0 │ X │ d e f │ = │ d*y e*y f*y │
     * │ 0 0 1 │   │ g h i │   │ g   h   i   │
     * └       ┘   └       ┘   └             ┘
     */
    fun scale (x: Float, y: Float) {
        for (i in 0 .. 3) {
            values[i] *= x
            values[i + 3] *= y
        }
    }

    /**
     * ┌       ┐   ┌       ┐   ┌                       ┐
     * │ 1 0 0 │   │ a b c │   │ a         b         c │
     * │ 0 1 0 │ X │ d e f │ = │ d         e         f │
     * │ x y 1 │   │ g h i │   │ a*x+d*y+g b*x+e*y+h i │
     * └       ┘   └       ┘   └                       ┘
     */
    fun translate (x: Float, y: Float) {
        values[6] += values[0] * x + values[3] * y
        values[7] += values[1] * x + values[4] * y
    }



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        if (!values.contentEquals((other as Matrix).values)) return false
        return true
    }

    override fun hashCode() = values.contentHashCode()
}