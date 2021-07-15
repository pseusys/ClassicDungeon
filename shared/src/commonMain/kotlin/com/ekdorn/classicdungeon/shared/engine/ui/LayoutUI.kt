package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.maths.Vector


// TODO: inner border
internal open class LayoutUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ResizableUI(initializer) {
    @Implicit private val children = mutableListOf<WidgetUI>()

    var background: FrameUI? = null
        set (v) {
            if (v != null) v.parent = this
            else field?.parent = null
            field = v
        }


    init {
        // background = initializer.getOrElse("border") { border } as Vector
    }



    fun add (element: WidgetUI) {
        element.parent = this
        children.add(element)
    }

    fun remove (element: WidgetUI) {
        element.parent = null
        children.remove(element)
    }



    override fun update (elapsed: Int) {
        super.update(elapsed)
        background?.let {
            it.translate(coords, metrics)
            it.update(elapsed)
        }
        val innerBorder = if (background != null) background!!.pixelBorder() else Vector()
        children.forEach { if (it.visible) {
            it.translate(coords + innerBorder, metrics - innerBorder * 2F)
            it.update(elapsed)
        } }
    }

    override fun draw () {
        super.draw()
        background?.draw()
        children.forEach { if (it.visible) it.draw() }
    }



    override fun delete() {
        super.delete()
        background?.delete()
        children.forEach { it.delete() }
    }
}
