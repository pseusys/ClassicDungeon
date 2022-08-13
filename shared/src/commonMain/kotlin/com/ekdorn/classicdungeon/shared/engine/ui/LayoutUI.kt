package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.engine.ui.extensions.Clickable
import com.ekdorn.classicdungeon.shared.engine.ui.extensions.Movable
import com.ekdorn.classicdungeon.shared.engine.ui.extensions.Zoomable
import com.ekdorn.classicdungeon.shared.engine.utils.*
import com.ekdorn.classicdungeon.shared.engine.utils.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.reflect.KProperty


/**
 * LayoutUI - container for widgets.
 */
@Serializable
@SerialName("LayoutUI")
internal open class LayoutUI: ResizableUI() {
    /**
     * Children list.
     */
    protected val children = mutableMapOf<String, WidgetUI>()


    inline val childCoords: Vector
        get() = coords + innerBorder

    inline val childMetrics: Vector
        get() = metrics - innerBorder * 2F

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


    @Transient private var innerBorder = Vector()

    @Suppress("UNCHECKED_CAST")
    @Transient private val parents = children.filterValues { it is LayoutUI }.toMutableMap() as MutableMap<String, LayoutUI>


    init { background?.parent = this }


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

    override fun translate() {
        super.translate()
        innerBorder = Vector()
        background?.translate()
        innerBorder = if (background != null) background!!.pixelBorder * background!!.pixelation else Vector()
        children.values.forEach { if (it.visible) it.translate() }
    }

    fun requestTranslate(widget: WidgetUI) = widget.translate()

    override fun delete() {
        super.delete()
        background?.delete()
        children.values.forEach { it.delete() }
    }


    private fun WidgetUI.bubbleThrough(event: Event) = this is LayoutUI && this.bubble(event)

    private fun WidgetUI.click(event: ClickEvent) = this is Clickable && this.onClick(event.position, event.type, event.mode)

    private fun WidgetUI.move(event: MoveEvent) = this is Movable && this.onMove(event.start, event.end)

    private fun WidgetUI.zoom(event: ZoomEvent) = this is Zoomable && this.onZoom(event.position, event.level)

    open fun bubble(event: Event): Boolean = children.lastNotNullOfOrNull {
        val widget = it.value
        when (event) {
            is ClickEvent -> if (widget.rect.includes(event.position) && (widget.click(event) || widget.bubbleThrough(event))) true else null
            is MoveEvent -> if (widget.rect.includes(event.start) && widget.rect.includes(event.end) && (widget.move(event) || widget.bubbleThrough(event))) true else null
            is ZoomEvent -> if (widget.rect.includes(event.position) && (widget.zoom(event) || widget.bubbleThrough(event))) true else null
            else -> null
        }
    } ?: false


    @Suppress("UNCHECKED_CAST")
    operator fun <T: WidgetUI> getValue(thisRef: LayoutUI, property: KProperty<*>) = children[property.name] as T?

    operator fun <T: WidgetUI> setValue(thisRef: LayoutUI, property: KProperty<*>, value: T?) = if (value != null) add(property.name, value) else Unit


    override fun toString() = "${super.toString()} children [${children.keys.joinToString()}]"
}
