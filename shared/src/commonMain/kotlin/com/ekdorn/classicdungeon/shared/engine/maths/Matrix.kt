package com.ekdorn.classicdungeon.shared.engine.maths

import com.ekdorn.classicdungeon.shared.engine.lib.str
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

internal data class Matrix (val values: FloatArray) {
    constructor (
        i11: Float, i12: Float, i13: Float, i14: Float,
        i21: Float, i22: Float, i23: Float, i24: Float,
        i31: Float, i32: Float, i33: Float, i34: Float,
        i41: Float, i42: Float, i43: Float, i44: Float
    ): this(floatArrayOf(i11, i12, i13, i14, i21, i22, i23, i24, i31, i32, i33, i34, i41, i42, i43, i44))

    constructor (): this(
        1F, 0F, 0F, 0F,
        0F, 1F, 0F, 0F,
        0F, 0F, 1F, 0F,
        0F, 0F, 0F, 1F
    )


    private fun toRadians (deg: Float) = (deg * PI / 180).toFloat()


    fun toIdentity () = values.forEachIndexed { index, _ -> values[index] = if (index % 5 == 0) 1F else 0F }

    /**
     * ┌                    ┐   ┌         ┐   ┌                                                                             ┐
     * │ cos(t)  sin(t) 0 0 │   │ a b c d │   │ a*cos(t)+e*sin(t)  b*cos(t)+f*sin(t)  c*cos(t)+g*sin(t)  d*cos(t)+h*sin(t)  │
     * │ -sin(t) cos(t) 0 0 │ X │ e f g h │ = │ -a*sin(t)+e*cos(t) -b*sin(t)+f*cos(t) -c*sin(t)+g*cos(t) -d*sin(t)+h*cos(t) │
     * │ 0       0      1 0 │   │ i j k l │   │ i                  j                  k                  l                  │
     * │ 0       0      0 1 │   │ m n o p │   │ m                  n                  o                  p                  │
     * └                    ┘   └         ┘   └                                                                             ┘
     */
    fun rotate (degrees: Float) {
        if (degrees == 0F) return
        val radians = toRadians(degrees)
        val sin = sin(radians)
        val cos = cos(radians)
        val mat = values.copyOf()
        for (i in 0 .. 4) {
            values[i] = cos * mat[i] + sin * mat[i + 4]
            values[i + 3] = -sin * mat[i] + cos * mat[i + 4]
        }
    }

    /**
     * ┌              ┐   ┌         ┐   ┌                                             ┐
     * │ 1      0 0 0 │   │ a b c d │   │ a          b          c          d          │
     * │ tan(t) 1 0 0 │ X │ e f g h │ = │ a*tan(t)+e b*tan(t)+f c*tan(t)+g d*tan(t)+h │
     * │ 0      0 1 0 │   │ i j k l │   │ i          j          k          l          │
     * │ 0      0 0 1 │   │ m n o p │   │ m          n          o          p          │
     * └              ┘   └         ┘   └                                             ┘
     */
    fun sheerX (degrees: Float) {
        if (degrees == 0F) return
        val tan = tan(toRadians(degrees))
        for (i in 0 .. 3) values[i + 4] += values[i] * tan
    }

    /**
     * ┌              ┐   ┌         ┐   ┌                                             ┐
     * │ 1 tan(t) 0 0 │   │ a b c d │   │ a+e*tan(t) b+f*tan(t) c+g*tan(t) d+h*tan(t) │
     * │ 0 1      0 0 │ X │ e f g h │ = │ e          f          g          h          │
     * │ 0 0      1 0 │   │ i j k l │   │ i          j          k          l          │
     * │ 0 0      0 1 │   │ m n o p │   │ m          n          o          p          │
     * └              ┘   └         ┘   └                                             ┘
     */
    fun sheerY (degrees: Float) {
        if (degrees == 0F) return
        val tan = tan(toRadians(degrees))
        for (i in 0 .. 3) values[i] += values[i + 4] * tan
    }

    /**
     * ┌         ┐   ┌         ┐   ┌                 ┐
     * │ x 0 0 0 │   │ a b c d │   │ a*x b*x c*x d*x │
     * │ 0 y 0 0 │ X │ e f g h │ = │ e*y f*y g*y h*y │
     * │ 0 0 1 0 │   │ i j k l │   │ i   j   k   l   │
     * │ 0 0 0 1 │   │ m n o p │   │ m   n   o   p   │
     * └         ┘   └         ┘   └                 ┘
     */
    fun scale (x: Float, y: Float) {
        for (i in 0 .. 3) {
            values[i] *= x
            values[i + 4] *= y
        }
    }

    /**
     * ┌         ┐   ┌         ┐   ┌                                         ┐
     * │ 1 0 0 0 │   │ a b c d │   │ a         b         c         d         │
     * │ 0 1 0 0 │ X │ e f g h │ = │ d         e         f         h         │
     * │ 0 0 1 0 │   │ i j k l │   │ i         j         k         l         │
     * │ x y 0 1 │   │ m n o p │   │ a*x+d*y+m b*x+e*y+n c*x+f*y+o d*x+h*y+p │
     * └         ┘   └         ┘   └                                         ┘
     */
    fun translate (x: Float, y: Float) {
        for (i in 0 .. 3) values[i + 12] += values[i] * x + values[i + 4] * y
    }



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        if (!values.contentEquals((other as Matrix).values)) return false
        return true
    }

    override fun hashCode() = values.contentHashCode()


    override fun toString() = StringBuilder().apply {
        val rowLen = 8 + 11 * 4
        append("Matrix 4x4:\n").append('┌').apply { for (i in 0 until rowLen - 3) append(' ') }.append("┐\n")
        for (i in 0 .. 3) {
            append("│ ")
            for (j in 0 .. 3) append(values[i * 4 + j].str(5, 5), ' ')
            append("│\n")
        }
        append('└').apply { for (i in 0 until rowLen - 3) append(' ') }.append("┘\n")
    }.toString()
}