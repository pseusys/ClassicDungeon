package com.ekdorn.classicdungeon.shared.engine.ui.extensions

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.utils.ClickEvent
import kotlinx.serialization.Transient


internal interface Clickable: Movable {
    @Transient val clickable: Boolean
        get() = true

    fun onClick(pos: Vector, type: ClickEvent.ClickType, mode: ClickEvent.ClickMode) = when {
        type == ClickEvent.ClickType.DOWN && mode == ClickEvent.ClickMode.PRIMARY -> onPrimaryClickDown(pos)
        type == ClickEvent.ClickType.UP && mode == ClickEvent.ClickMode.PRIMARY -> onPrimaryClickUp(pos)
        type == ClickEvent.ClickType.DOWN && mode == ClickEvent.ClickMode.SECONDARY -> onSecondaryClickDown(pos)
        type == ClickEvent.ClickType.UP && mode == ClickEvent.ClickMode.SECONDARY -> onSecondaryClickUp(pos)
        else -> clickable
    }


    fun onPrimaryClickDown(pos: Vector) = clickable

    fun onPrimaryClickUp(pos: Vector) = clickable

    fun onSecondaryClickDown(pos: Vector) = clickable

    fun onSecondaryClickUp(pos: Vector) = clickable
}
