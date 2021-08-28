package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.maths.Vector


/**
 * LayoutUI - container for widgets.
 */
internal open class LayoutUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ResizableUI(initializer) {
    /**
     * Children list.
     */
    @Implicit private val children = mutableListOf<WidgetUI>()

    /**
     * Property background - special FrameUI child, representing this layout background.
     * Null by default.
     */
    var background: FrameUI? = null
        set (v) {
            if (v != null) v.parent = this
            else field?.parent = null
            field = v
        }



    /**
     * Method to add child to the layout.
     * @param element widget to add
     */
    fun add (element: WidgetUI) {
        element.parent = this
        children.add(element)
    }

    /**
     * Method to remove child to the layout.
     * @param element widget to remove
     */
    fun remove (element: WidgetUI) {
        element.parent = null
        children.remove(element)
    }



    override fun update (elapsed: Int) {
        super.update(elapsed)
        background?.update(elapsed)
        children.forEach { if (it.visible) { it.update(elapsed) } }
    }

    override fun draw () {
        super.draw()
        background?.draw()
        children.forEach { if (it.visible) it.draw() }
    }



    override fun translate(parentCoords: Vector, parentMetrics: Vector) {
        super.translate(parentCoords, parentMetrics)
        background?.translate(coords, metrics)
        val innerBorder = if (background != null) background!!.pixelBorder * background!!.pixelation else Vector()
        children.forEach { if (it.visible) it.translate(coords + innerBorder, metrics - innerBorder * 2F) }
    }



    override fun delete() {
        super.delete()
        background?.delete()
        children.forEach { it.delete() }
    }
}
