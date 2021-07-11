package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.maths.Rectangle


internal abstract class WidgetUI (rect: Rectangle) {
    var exists: Boolean = true
    private var alive: Boolean = true
    var active: Boolean = true
        get() = field && if (parent != null) (parent!!.active) else true
    var visible: Boolean = true
        get() = field && if (parent != null) (parent!!.visible) else true

    val coords = Vector()
    val metrics = Vector()

    open var parent: LayoutUI? = null

    init {
        coords.x = rect.left
        coords.y = rect.top
        metrics.x = rect.right
        metrics.y = rect.bottom
    }



    inline var center: Vector
        get () = Vector(coords.x + metrics.x / 2, coords.y + metrics.y / 2)
        set (v) {
            coords.x = v.x - metrics.x / 2
            coords.y = v.y - metrics.y / 2
        }


    abstract fun update (elapsed: Int)

    abstract fun draw ()


    fun detach () {
        parent = null
    }
    fun attach (root: LayoutUI) {
        parent = root
    }

    fun kill () {
        alive = false
        exists = false
    }
    fun revive () {
        alive = true
        exists = true
    }

    abstract fun delete ()
}
