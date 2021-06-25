package com.ekdorn.classicdungeon.shared.ui

internal class LayoutUI: WidgetUI() {
    val children = mutableListOf<WidgetUI>()

    override fun update(elapsed: Double) {
        children.forEach { if (it.exists && it.visible) it.update(elapsed) }
    }

    override fun draw() {
        children.forEach { if (it.exists && it.visible) it.draw() }
    }
}
