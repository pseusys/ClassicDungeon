package com.ekdorn.classicdungeon.shared.gl.extensions

import com.ekdorn.classicdungeon.shared.gl.wrapper.GLBuffer
import com.ekdorn.classicdungeon.shared.engine.atomic.Rectangle


/**
 * Class, representing GL array buffer used in every widget to keep vertex and texture data.
 */
internal class WidgetBuffer: GLBuffer(TYPE.COMMON) {
    /**
     * Function, converting any collection of rectangles to FloatArray.
     * @return array of floats, representing rectangle edges.
     */
    private fun Collection<Rectangle>.toFloatArray () = flatMap { it.toPointsArray().asIterable() }.toFloatArray()

    /**
     * Function for loading given vertices and textures arrays into this widgets buffer.
     * @param vertices array containing coordinates of this widget
     * @param textures array containing texture points associated with this widget coordinates
     */
    fun fill (vertices: Collection<Rectangle>, textures: Collection<Rectangle>) = fill(vertices.toFloatArray(), textures.toFloatArray())

    /**
     * Function for loading given vertices and textures array into this widgets buffer.
     * @param vertices array containing coordinates of this widget
     * @param textures array containing texture points associated with this widget coordinates
     */
    fun fill (vertices: Rectangle, textures: Rectangle) = fill(vertices.toPointsArray(), textures.toPointsArray())

    /**
     * Function for loading given floats into this widgets buffer.
     * Floats are loaded as consequent coordinates, in pairs with offset of two.
     * @param vertices array containing coordinates of this widget
     * @param textures array containing texture points associated with this widget coordinates
     */
    private fun fill (vertices: FloatArray, textures: FloatArray) {
        val size = vertices.size + textures.size
        fillDynamic(FloatArray(size) {
            if ((it / 2) % 2 == 0) vertices[(it / 2) + (it % 2) - (it / 2) % 2]
            else textures[(it / 2) + (it % 2) - (it / 2) % 2]
        })
    }
}
