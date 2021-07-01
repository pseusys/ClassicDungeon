package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.maths.Matrix
import com.ekdorn.classicdungeon.shared.maths.Vector
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Rectangle


internal open class WidgetUI (var parent: LayoutUI? = null) {
    var exists: Boolean = true
    private var alive: Boolean = true
    var active: Boolean = true
        get() = field && if (parent != null) (parent!!.active) else true
    var visible: Boolean = true
        get() = field && if (parent != null) (parent!!.visible) else true

    val coords = Vector()
    var width = 0F
        get() = field * scale.x
    var height = 0F
        get() = field * scale.y

    // To parent percents, actual values to matrix
    private val speed = Vector()
    private val acceleration = Vector()
    private var angle = 0F
    private val angleSpeed = 0F

    private val scale = Vector(1F, 1F)
    private val origin = Vector()
    protected val model = Matrix()

    protected val ambient = Color(1F, 1F, 1F, 1F)
    protected val material = Color()

    constructor (x: Float, y: Float, w: Float, h: Float): this() {
        coords.x = x
        coords.y = y
        width = w
        height = h
    }

    constructor (rect: Rectangle): this(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top)



    inline var center: Vector
        get () = Vector(coords.x + width / 2, coords.y + height / 2)
        set (v) {
            coords.x = v.x - width / 2
            coords.y = v.y - height / 2
        }

    inline var alpha: Float
        get () = material.a + ambient.a
        set (v) {
            material.a = v
            ambient.a = 0F
        }


    private fun speed (speed: Float, acceleration: Float, time: Float) = speed + acceleration * time

    open fun update (elapsed: Float) {
        val halfDeltaX = (speed(speed.x, acceleration.x, elapsed) - speed.x) / 2
        speed.x += halfDeltaX
        coords.x += speed.x * elapsed
        speed.x += halfDeltaX

        val halfDeltaY = (speed(speed.y, acceleration.y, elapsed) - speed.y) / 2
        speed.y += halfDeltaY
        coords.y += speed.y * elapsed
        speed.y += halfDeltaY

        angle += angleSpeed * elapsed
    }

    open fun draw () = updateModel()

    private fun updateModel() {
        model.toIdentity()
        model.translate(coords.x, coords.y)
        model.translate(origin.x, origin.y)
        model.rotate(angle)
        model.scale(scale.x, scale.y)
        model.translate(-origin.x, -origin.y)
    }



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

    open fun delete () {}



    fun rect () = Rectangle(coords.x, coords.y, coords.x + width, coords.y + height)
}
