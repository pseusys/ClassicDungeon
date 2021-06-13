package com.ekdorn.classicdungeon.shared.glwrapper

import com.ekdorn.classicdungeon.shared.dependant.GLFunctions
import com.ekdorn.classicdungeon.shared.maths.Color

internal abstract class Texture {
    protected var id = -1

    init {
        id = GLFunctions.generate()
        bind()
        release()
    }


    fun bind (): Unit = GLFunctions.bind(id)
    fun activate (): Unit = GLFunctions.activate(id)
    fun release (): Unit = GLFunctions.release(id)
    fun delete (): Unit = GLFunctions.delete(id)

    fun filter (min: GLFunctions.FILTERING_MODE, mag: GLFunctions.FILTERING_MODE) {
        bind()
        GLFunctions.filter(min, mag)
        release()
    }
    fun wrap (s: GLFunctions.WRAPPING_MODE, t: GLFunctions.WRAPPING_MODE) {
        bind()
        GLFunctions.wrap(s, t)
        release()
    }

    fun fill (width: Int, height: Int, pixels: Array<Color>) {
        bind()
        GLFunctions.image(width, height, pixels.map { it.int }.toIntArray())
        release()
    }
}
