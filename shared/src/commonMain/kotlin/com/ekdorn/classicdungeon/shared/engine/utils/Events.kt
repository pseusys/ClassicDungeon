package com.ekdorn.classicdungeon.shared.engine.utils

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector


internal sealed class Event


internal data class ClickEvent(val type: ClickType, val mode: ClickMode, val position: Vector): Event() {
    enum class ClickType { UP, DOWN }
    enum class ClickMode { PRIMARY, SECONDARY }
}

internal data class MoveEvent(val mode: ClickEvent.ClickMode, val start: Vector, val end: Vector): Event()

internal class BackEvent: Event()

internal class KeyEvent: Event()

internal data class ZoomEvent(val position: Vector, val level: Int): Event()
