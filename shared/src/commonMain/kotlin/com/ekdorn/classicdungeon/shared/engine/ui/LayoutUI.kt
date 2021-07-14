package com.ekdorn.classicdungeon.shared.engine.ui


// FINAL
internal open class LayoutUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ResizableUI(initializer) {
    @Implicit
    private val children = mutableListOf<WidgetUI>()


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
        children.forEach { if (it.visible) {
            it.translate(coords, metrics)
            it.update(elapsed)
        } }
    }

    override fun draw () {
        super.draw()
        children.forEach { if (it.visible) it.draw() }
    }


    override fun delete() {
        super.delete()
        children.forEach { it.delete() }
    }
}
