package com.ekdorn.classicdungeon.shared.glwrapper

import com.ekdorn.classicdungeon.shared.dependant.GLFunctions
import com.ekdorn.classicdungeon.shared.maths.Color

internal abstract class Texture {
    var id = -1
        private set

    init {
        createId()
    }


    // Consider using TEXTURE_ARRAY_2D for items, eg
    protected fun createId () {
        id = GLFunctions.Texture.generate()
        bind()
        release()
    }

    fun bind (): Unit = GLFunctions.Texture.bind(id)
    //fun activate (): Unit = GLFunctions.Texture.activate(id)
    fun release (): Unit = GLFunctions.Texture.release(id)
    fun delete (): Unit = GLFunctions.Texture.delete(id)

    open fun filter (min: GLFunctions.Texture.FILTERING_MODE, mag: GLFunctions.Texture.FILTERING_MODE) {
        bind()
        GLFunctions.Texture.filter(min, mag)
        release()
    }

    open fun wrap (s: GLFunctions.Texture.WRAPPING_MODE, t: GLFunctions.Texture.WRAPPING_MODE) {
        bind()
        GLFunctions.Texture.wrap(s, t)
        release()
    }


    protected fun fill (width: Int, height: Int, pixels: ByteArray) {
        bind()
        GLFunctions.Texture.image(width, height, pixels)
        release()
    }
}
