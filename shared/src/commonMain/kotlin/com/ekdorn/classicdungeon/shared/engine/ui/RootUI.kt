package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.IO
import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.utils.ClickEvent
import com.ekdorn.classicdungeon.shared.engine.utils.Event
import com.ekdorn.classicdungeon.shared.engine.utils.MoveEvent


/**
 * RootUI - root layout of the game.
 * Has some extra functions compared to LayoutUI, for example, clears screen on update.
 */
internal class RootUI (screenWidth: Int, screenHeight: Int): LayoutUI() {
    private var misplaced = true

    init {
        coords = Vector(0F, 0F)
        metrics = Vector(screenWidth, screenHeight)
        dimens = Vector(1F, 1F)
        verticalAlignment = ALIGNMENT.START
        horizontalAlignment = ALIGNMENT.START

        IO.interactiveEvents.add(::bubble)
    }


    override fun bubble(event: Event) = if (event is MoveEvent) {
        var move = super.bubble(event)
        val endTouch = ClickEvent(ClickEvent.ClickType.UP, event.mode, event.start)
        val startTouch = ClickEvent(ClickEvent.ClickType.DOWN, event.mode, event.end)
        if (!move) move = super.bubble(endTouch) || super.bubble(startTouch)
        move
    } else super.bubble(event)

    override fun add(id: String, element: WidgetUI) {
        super.add(id, element)
        misplaced = true
    }

    /**
     * Single method to update and draw whole widgets tree.
     * Clears screen before update.
     * @param elapsed time since previous update in milliseconds
     */
    fun enter (elapsed: Int) {
        GLFunctions.clear()
        if (misplaced) translate()
        update(elapsed)
        draw()
    }

    /**
     * Method triggered each time screen gets resized, resizes whole widgets tree.
     */
    fun resize (screenWidth: Int, screenHeight: Int) {
        metrics = Vector(screenWidth, screenHeight)
        misplaced = true
    }

    override fun translate() {
        misplaced = false
        children.values.forEach { if (it.visible) it.translate() }
    }
}
