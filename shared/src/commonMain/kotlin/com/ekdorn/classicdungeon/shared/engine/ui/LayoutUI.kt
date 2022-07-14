package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


/**
 * LayoutUI - container for widgets.
 */
@Serializable
internal open class LayoutUI: ResizableUI() {
    /**
     * Children list.
     */
    @Transient private val children = mutableMapOf<String, WidgetUI>()

    @Suppress("UNCHECKED_CAST")
    @Transient private val parents = children.filterValues { it is LayoutUI }.toMutableMap() as MutableMap<String, LayoutUI>


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
     * @param id id of new element
     * @param element widget to add
     */
    open fun add (id: String, element: WidgetUI) {
        element.parent = this
        children[id] = element
        if (element is LayoutUI) parents[id] = element
    }

    /**
     * Method to get child from the layout.
     * @param id id of the widget to get
     */
    @Suppress("UNCHECKED_CAST")
    operator fun <Type: WidgetUI> get (id: String): Type? {
        if (id in children) return children[id] as Type
        for (parent in parents.values) {
            val child = parent.get<Type>(id)
            if (child != null) return child
        }
        return null
    }

    /**
     * Method to remove child from the layout.
     * @param id id of the widget to remove
     */
    fun remove (id: String) {
        children[id]?.parent = null
        parents.remove(id)
        children.remove(id)
    }

    /**
     * Method to remove all children from the layout.
     */
    fun clear () = children.clear()

    fun populate(descendants: Map<String, WidgetUI>) {
        children.clear()
        descendants.forEach { add(it.key, it.value) }
    }

    override fun update (elapsed: Int) {
        super.update(elapsed)
        background?.update(elapsed)
        children.values.forEach { if (it.visible) { it.update(elapsed) } }
    }

    override fun draw () {
        super.draw()
        background?.draw()
        children.values.forEach { if (it.visible) it.draw() }
    }

    override fun translate(parentCoords: Vector, parentMetrics: Vector) {
        super.translate(parentCoords, parentMetrics)
        background?.translate(coords, metrics)
        val innerBorder = if (background != null) background!!.pixelBorder * background!!.pixelation else Vector()
        children.values.forEach { if (it.visible) it.translate(coords + innerBorder, metrics - innerBorder * 2F) }
    }

    override fun delete() {
        super.delete()
        background?.delete()
        children.values.forEach { it.delete() }
    }
}
