package com.ekdorn.classicdungeon.shared.engine.atomic

import kotlinx.serialization.Serializable


/**
 * Class, representing RGBA color.
 * - r - red component.
 * - g - green component.
 * - b - blue component.
 * - a - transparency component.
 * TODO: migrate to UByteArray once its not experimental anymore.
 */
@Serializable
internal data class Color (var value: FloatArray) {
    constructor (r: Float, g: Float, b: Float, a: Float): this(floatArrayOf(r, g, b, a))
    constructor (): this(0F, 0F, 0F, 0F)

    constructor (value: ByteArray): this(value.map { it.toUByte().toFloat() / 255 }.toFloatArray())
    constructor (r: Byte, g: Byte, b: Byte, a: Byte): this(byteArrayOf(r, g, b, a))

    constructor (int: UInt): this((int shr 24).toByte(), (int shr 16).toByte(), (int shr 8).toByte(), int.toByte())

    /**
     * Inline converter to and from 32bit unsigned int (0xRRGGBBAA).
     */
    inline var int: UInt
        get () = value.foldIndexed(0U) { i, a, v -> a or ((v * 255).toUInt() and 0xFFU shl ((value.size - 1 - i) * 8)) }
        set (v) = value.indices.forEach { i -> value[i] = (v shr ((value.size - 1 - i) * 8) and 0xFFU).toFloat() / 255 }

    /**
     * Inline converter to and from Byte array ([r, g, b, a]).
     */
    inline var bytes: ByteArray
        get () = value.map { (it * 255).toInt().toByte() }.toByteArray()
        set (v) = value.indices.forEach { value[it] = v[it].toUByte().toFloat() / 255 }



    /**
     * Inline alias for red color component.
     */
    inline var r: Float
        get () = value[0]
        set (v) { value[0] = v }

    /**
     * Inline alias for green color component.
     */
    inline var g: Float
        get () = value[1]
        set (v) { value[1] = v }

    /**
     * Inline alias for blue color component.
     */
    inline var b: Float
        get () = value[2]
        set (v) { value[2] = v }

    /**
     * Inline alias for alpha color component.
     */
    inline var a: Float
        get () = value[3]
        set (v) { value[3] = v }


    /**
     * Default overridden equals and hashCode methods and custom toString.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Color

        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }


    override fun toString() = "Color(r: $r, g: $g, b: $b, a: $a)"
}
