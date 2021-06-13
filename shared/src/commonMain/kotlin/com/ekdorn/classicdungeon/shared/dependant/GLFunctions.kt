package com.ekdorn.classicdungeon.shared.dependant


expect object GLFunctions {
    enum class FILTERING_MODE {
        NEAREST, LINEAR
    }

    enum class WRAPPING_MODE {
        NEAREST, LINEAR
    }

    fun generate (): Int
    fun activate (texture: Int)
    fun bind (texture: Int)
    fun release (texture: Int)
    fun delete (texture: Int)

    fun filter (minification: FILTERING_MODE, magnification: FILTERING_MODE)
    fun wrap (s: WRAPPING_MODE, t: WRAPPING_MODE)

    fun image (w: Int, h: Int, data: IntArray)
}
