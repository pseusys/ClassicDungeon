package com.ekdorn.classicdungeon.shared.dependant.gl

expect open class GLTexture () {
    enum class FILTERING {
        NEAREST, LINEAR
    }

    // which ones needed?
    enum class WRAPPING {
        CLAMP
    }


    val id: Int


    fun generate ()
    //fun activate(texture: Int)
    fun bind ()
    fun release ()
    fun delete ()

    open fun filter (minification: FILTERING, magnification: FILTERING)
    open fun wrap (s: WRAPPING, t: WRAPPING)

    fun image (w: Int, h: Int, data: ByteArray)
}