package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.dependant.gl.GLFunctions
import com.ekdorn.classicdungeon.shared.engine.maths.Rectangle
import com.ekdorn.classicdungeon.shared.engine.maths.Vector


/**
 * RootUI - root layout of the game.
 * Has some extra functions compared to LayoutUI, for example, clears screen on update.
 */
internal class RootUI (screenWidth: Int, screenHeight: Int): LayoutUI(hashMapOf<String, Any>()) {
    init {
        coords = Vector(0F, 0F)
        metrics = Vector(screenWidth, screenHeight)
        dimens = Vector(1F, 1F)
        verticalAlignment = ALIGNMENT.START
        horizontalAlignment = ALIGNMENT.START
    }



    /**
     * Single method to update and draw whole widgets tree.
     * Clears screen before update.
     * @param elapsed time since previous update in milliseconds
     */
    fun enter (elapsed: Int) {
        GLFunctions.clear()
        translate(coords, metrics)
        update(elapsed)
        draw()
    }

    /**
     * Method triggered each time screen gets resized, resizes whole widgets tree.
     */
    fun resize (screenWidth: Int, screenHeight: Int) {
        metrics = Vector(screenWidth, screenHeight)
    }
}
