package com.ekdorn.classicdungeon.shared.gl.wrapper


/**
 * GLTexture class represents a GL texture.
 *
 * [OpenGL wiki entry](https://www.khronos.org/opengl/wiki/Texture)
 */
expect open class GLTexture () {
    /**
     * Types of the texture filtering:
     * - NEAREST: texture breaks up into pixels when maximized.
     * - LINEAR: texture gets blurred when maximized.
     */
    enum class FILTERING {
        NEAREST, LINEAR
    }

    // TODO: specify WRAPPING
    // which ones needed?
    enum class WRAPPING {
        CLAMP
    }


    /**
     * Texture id. Not needed in WebGL, therefore set to 1.
     */
    val id: Int


    /**
     * Function, generating empty texture.
     */
    fun generate ()

    // TODO: activation needed?
    //fun activate(texture: Int)

    /**
     * Function, binding this texture.
     * Should be called before draw event.
     * @see GLFunctions.drawElements draw event
     */
    fun bind ()

    /**
     * Function, releasing this texture.
     * Should be called after draw event.
     * @see GLFunctions.drawElements draw event
     */
    fun release ()

    /**
     * Function, deleting this texture.
     */
    fun delete ()


    /**
     * Function, setting this textures filtering.
     * @param minification minification filtering
     * @param magnification magnification filtering
     */
    open fun filter (minification: FILTERING, magnification: FILTERING)

    /**
     * Function, setting this textures wrapping.
     * @param s horizontal wrapping
     * @param t vertical wrapping
     */
    open fun wrap (s: WRAPPING, t: WRAPPING)

    /**
     * Function, setting pixel data to this texture.
     * @param w texture width
     * @param h texture height
     * @param data pixel data
     */
    fun image (w: Int, h: Int, data: ByteArray)
}