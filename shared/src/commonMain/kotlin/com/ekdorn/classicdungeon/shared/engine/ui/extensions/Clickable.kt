package com.ekdorn.classicdungeon.shared.engine.ui.extensions

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.utils.ClickEvent
import kotlinx.serialization.Transient


internal interface Clickable: Movable {
    @Transient var primaryClicked: Boolean
    @Transient var secondaryClicked: Boolean

    @Transient val clickable: Boolean
        get() = true

    fun onClick(pos: Vector, type: ClickEvent.ClickType, mode: ClickEvent.ClickMode) = when {
        type == ClickEvent.ClickType.DOWN && mode == ClickEvent.ClickMode.BOTH -> onPrimaryClickDown(pos) || onSecondaryClickDown(pos)
        type == ClickEvent.ClickType.UP && mode == ClickEvent.ClickMode.BOTH -> (primaryClicked && onPrimaryClickUp(pos)) || (secondaryClicked && onSecondaryClickUp(pos))
        type == ClickEvent.ClickType.DOWN && mode == ClickEvent.ClickMode.PRIMARY -> onPrimaryClickDown(pos)
        type == ClickEvent.ClickType.UP && mode == ClickEvent.ClickMode.PRIMARY -> onPrimaryClickUp(pos)
        type == ClickEvent.ClickType.DOWN && mode == ClickEvent.ClickMode.SECONDARY -> onSecondaryClickDown(pos)
        type == ClickEvent.ClickType.UP && mode == ClickEvent.ClickMode.SECONDARY -> onSecondaryClickUp(pos)
        else -> clickable
    }


    fun onPrimaryClickDown(pos: Vector): Boolean {
        primaryClicked = true
        return clickable
    }

    fun onPrimaryClickUp(pos: Vector): Boolean {
        primaryClicked = false
        return clickable
    }

    fun onSecondaryClickDown(pos: Vector): Boolean {
        secondaryClicked = true
        return clickable
    }

    fun onSecondaryClickUp(pos: Vector): Boolean {
        secondaryClicked = false
        return clickable
    }

    override fun onMove(start: Vector, end: Vector) = super.onMove(start, end) && (primaryClicked || secondaryClicked)
}
