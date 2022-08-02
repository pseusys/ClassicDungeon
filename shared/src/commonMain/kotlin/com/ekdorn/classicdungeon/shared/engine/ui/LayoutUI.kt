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
import kotlin.reflect.KClass


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

    protected open fun specialChildren(): Map<String, KClass<out WidgetUI>> = mapOf()


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
        val special = specialChildren()
        assert(special[id]?.isInstance(element) ?: true) {
            "Child '$id' of '${this::class.simpleName}' should be ${special[id]!!.simpleName}, not ${element::class.simpleName}!"
        }
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


    open fun bubble(event: Event): Boolean = children.firstNotNullOfOrNull {
        val widget = it.value
        val direct = when (event) {
            is ClickEvent -> if (widget is Clickable && widget.rect.includes(event.position) && widget.onClick(event.position, event.type, event.mode)) true else null
            is MoveEvent -> if (widget is Movable && widget.rect.includes(event.start) && widget.rect.includes(event.end) && widget.onMove(event.start, event.end)) true else null
            is ZoomEvent -> if (widget is Zoomable && widget.rect.includes(event.position) && widget.onZoom(event.position, event.level)) true else null
            else -> null
        }
        if (direct == null && widget is LayoutUI) widget.bubble(event) else direct
    } ?: false
}
