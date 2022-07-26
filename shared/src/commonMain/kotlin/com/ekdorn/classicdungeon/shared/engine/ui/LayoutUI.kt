package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.ui.implementable.Interactive
import com.ekdorn.classicdungeon.shared.engine.ui.implementable.Movable
import com.ekdorn.classicdungeon.shared.engine.ui.implementable.Zoomable
import com.ekdorn.classicdungeon.shared.engine.utils.*
import com.ekdorn.classicdungeon.shared.engine.utils.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


/**
 * LayoutUI - container for widgets.
 */
@Serializable
@SerialName("LayoutUI")
internal open class LayoutUI: ResizableUI() {
    /**
     * Children list.
     */
    private val children = mutableMapOf<String, WidgetUI>()

    /**
     * Property background - special FrameUI child, representing this layout background.
     * Null by default.
     */
    var background: FrameUI? = null
        set (v) {
            field?.parent = null
            v?.parent = this
            field = v
        }


    @Suppress("UNCHECKED_CAST")
    @Transient private val parents = children.filterValues { it is LayoutUI }.toMutableMap() as MutableMap<String, LayoutUI>


    init {
        background?.parent = this
        children.values.forEach { it.parent = this }
    }


    /**
     * Method to add child to the layout.
     * @param id id of new element
     * @param element widget to add
     */
    open fun add (id: String, element: WidgetUI) {
        children[id]?.parent = null
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
        translateInnerChildren(coords + innerBorder, metrics - innerBorder * 2F)
    }

    // TODO: remove when delegate serialization for ancestors is enabled.
    open fun translateInnerChildren(parentCoords: Vector, parentMetrics: Vector) {
        children.values.forEach { if (it.visible) it.translate(parentCoords, parentMetrics) }
    }

    override fun delete() {
        super.delete()
        background?.delete()
        children.values.forEach { it.delete() }
    }


    open fun bubble(event: Event): Boolean = children.firstNotNullOfOrNull {
        val widget = it.value
        val direct = when (event) {
            is ClickEvent -> {
                if (widget is Interactive && widget.rect.includes(event.position)) {
                    if (if (event.type == ClickEvent.ClickType.DOWN) widget.onTouchDown(event.position) else widget.onTouchUp(event.position)) true else null
                } else null
            }
            is MoveEvent -> if (widget is Movable && widget.rect.includes(event.start) && widget.rect.includes(event.end) && widget.onMove(event.start, event.end)) true else null
            is ZoomEvent -> if (widget is Zoomable && widget.rect.includes(event.position) && widget.onZoom(event.position, event.level)) true else null
            else -> null
        }
        if (direct == null && widget is LayoutUI) widget.bubble(event) else direct
    } ?: false


    // TODO: enable delegate serialization in ancestors via:
    // FIXME: https://github.com/Kotlin/kotlinx.serialization/issues/1578
    // Use https://kotlinlang.org/docs/delegated-properties.html#providing-a-delegate for adding properties to children.
    /*
    operator fun <Widget: WidgetUI> getValue(thisRef: LayoutUI, property: KProperty<*>): Widget? {
        return get(property.name)
    }

    operator fun <Widget: WidgetUI> setValue(thisRef: LayoutUI, property: KProperty<*>, value: Widget?) {
        if (value != null) add(property.name, value)
        else remove(property.name)
    }
     */
}
