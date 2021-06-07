package com.ekdorn.classicdungeon.shared.maths

import kotlin.math.sqrt



internal sealed class Vector<Other> (protected val ind: DoubleArray) {
    fun coord (i: Int) = ind[i]
    fun coord (i: Int, v: Double) {
        ind[i] = v
    }

    abstract operator fun rangeTo (other: Other): Other
    abstract fun length (): Double

    fun copyCoords (other: Vector<*>) {
        ind.iterator().withIndex().forEach {
            if (it.index < other.ind.size) ind[it.index] = other.ind[it.index]
        }
    }

    override fun toString(): String {
        var coords = ""
        ind.iterator().withIndex().forEach {
            coords += "${it.index}: ${it.value}; "
        }
        return "Vector(${coords.substringBeforeLast("; ")})"
    }
}



internal class Vector2D (a: Double, b: Double): Vector<Vector2D>(DoubleArray(2)) {
    constructor (other: Vector<*>): this(0.0, 0.0) {
        copyCoords(other)
    }
    constructor (): this(0.0, 0.0)

    init {
        ind[0] = a
        ind[1] = b
    }

    inline var x: Double
        get () = coord(0)
        set (v) = coord(0, v)

    inline var y: Double
        get () = coord(1)
        set (v) = coord(1, v)

    override fun rangeTo (other: Vector2D) = Vector2D(x - other.coord(0), y - other.coord(1))
    override fun length () = sqrt(x * x + y * y)
}

internal class Vector3D (a: Double, b: Double, c: Double): Vector<Vector3D>(DoubleArray(3)) {
    constructor (other: Vector<*>): this(0.0, 0.0, 0.0) {
        copyCoords(other)
    }
    constructor (): this(0.0, 0.0, 0.0)

    init {
        ind[0] = a
        ind[1] = b
        ind[2] = c
    }

    inline var x: Double
        get () = coord(0)
        set (v) = coord(0, v)

    inline var y: Double
        get () = coord(1)
        set (v) = coord(1, v)

    inline var z: Double
        get () = coord(2)
        set (v) = coord(2, v)

    inline var r: Double
        get () = coord(0)
        set (v) = coord(0, v)

    inline var g: Double
        get () = coord(1)
        set (v) = coord(1, v)

    inline var b: Double
        get () = coord(2)
        set (v) = coord(2, v)

    override fun rangeTo (other: Vector3D) = Vector3D(x - other.coord(0), y - other.coord(1), z - other.coord(2))
    override fun length () = sqrt(x * x + y * y + z * z)
}

internal class Vector4D (a: Double, b: Double, c: Double, d: Double): Vector<Vector4D>(DoubleArray(4)) {
    constructor (other: Vector<*>): this(0.0, 0.0, 0.0, 0.0) {
        copyCoords(other)
    }
    constructor (): this(0.0, 0.0, 0.0, 0.0)

    init {
        ind[0] = a
        ind[1] = b
        ind[2] = c
        ind[3] = d
    }

    inline var x: Double
        get () = coord(0)
        set (v) = coord(0, v)

    inline var y: Double
        get () = coord(1)
        set (v) = coord(1, v)

    inline var z: Double
        get () = coord(2)
        set (v) = coord(2, v)

    inline var r: Double
        get () = coord(0)
        set (v) = coord(0, v)

    inline var g: Double
        get () = coord(1)
        set (v) = coord(1, v)

    inline var b: Double
        get () = coord(2)
        set (v) = coord(2, v)

    inline var a: Double
        get () = coord(3)
        set (v) = coord(3, v)

    override fun rangeTo (other: Vector4D) = Vector4D(x - other.coord(0), y - other.coord(1), z - other.coord(2), ind[3] - other.coord(3))
    override fun length () = sqrt(x * x + y * y + z * z + ind[3] * ind[3])
}
