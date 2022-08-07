package com.ekdorn.classicdungeon.shared.engine.utils

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector


internal sealed class Event


internal data class ClickEvent(val type: ClickType, val position: Vector, val mode: ClickMode = ClickMode.BOTH): Event() {
    enum class ClickType { UP, DOWN }
    enum class ClickMode { PRIMARY, SECONDARY, BOTH }
}

internal data class MoveEvent(val start: Vector, val end: Vector): Event()

internal class BackEvent: Event()

internal class KeyEvent: Event()

internal data class ZoomEvent(val position: Vector, val level: Int): Event()
