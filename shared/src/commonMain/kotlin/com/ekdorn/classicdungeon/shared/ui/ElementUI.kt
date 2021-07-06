package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.generics.Cloneable
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Matrix
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal open class ElementUI (rect: Rectangle): WidgetUI(rect), Cloneable<ElementUI> {
    private val speed = Vector()
    private val acceleration = Vector()
    private var angle = 0F
    private val angleSpeed = 0F

    // private val origin = Vector()
    protected val model = Matrix()

    protected val ambient = Color(1F, 1F, 1F, 1F)
    protected val material = Color()


    inline var alpha: Float
        get () = material.a + ambient.a
        set (v) {
            material.a = v
            ambient.a = 0F
        }


    override fun clone(): ElementUI {
        TODO("Not yet implemented")
    }


    private fun speed (speed: Float, acceleration: Float, time: Float) = speed + acceleration * time

    override fun update(elapsed: Float) {
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


    protected open fun updateModel () {
        parent?.let {
            val pixelCoords = it.pixelMetrics each coords
            val pixelDimens = it.pixelMetrics each metrics

            model.toIdentity()
            model.translate(pixelCoords.x, -pixelCoords.y)
            model.scale(pixelDimens.x, pixelDimens.y)
            // model.translate(origin.x, origin.y) // Needed?
            model.rotate(angle)
            // model.translate(-origin.x, -origin.y) // Needed?
        }
    }

    override fun draw() = updateModel()

    override fun delete() {}
}