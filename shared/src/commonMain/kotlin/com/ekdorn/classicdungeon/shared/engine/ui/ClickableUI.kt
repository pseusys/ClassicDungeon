package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector

internal interface ClickableUI {
    fun onPointerDown(vector: Vector): Boolean

    fun onPointerUp(vector: Vector) = false

    fun onPointerMoved(vector: Vector) = false
}
