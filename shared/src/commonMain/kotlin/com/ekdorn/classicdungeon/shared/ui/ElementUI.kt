package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.dependant.gl.GLBuffer
import com.ekdorn.classicdungeon.shared.generics.Cloneable
import com.ekdorn.classicdungeon.shared.glextensions.Camera
import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Matrix
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal abstract class ElementUI (rect: Rectangle): WidgetUI(rect) {
    private val speed = Vector()
    private val acceleration = Vector()
    private var angle = 0F
    private val angleSpeed = 0F

    // private val origin = Vector()
    private val model = Matrix()

    protected val ambient = Color(1F, 1F, 1F, 1F)
    protected val material = Color()

    private val buffer = GLBuffer(GLBuffer.TYPE.COMMON)


    inline var alpha: Float
        get () = material.a + ambient.a
        set (v) {
            material.a = v
            ambient.a = 0F
        }


    private fun speed (speed: Float, acceleration: Float, time: Float) = speed + acceleration * time

    override fun update (elapsed: Float) {
        val halfDeltaX = (speed(speed.x, acceleration.x, elapsed) - speed.x) / 2
        speed.x += halfDeltaX
        coords.x += speed.x * elapsed
        speed.x += halfDeltaX

        val halfDeltaY = (speed(speed.y, acceleration.y, elapsed) - speed.y) / 2
        speed.y += halfDeltaY
        coords.y += speed.y * elapsed
        speed.y += halfDeltaY

        angle += angleSpeed * elapsed

        parent?.let {
            val pixelCoords = it.pixelCoords + (it.pixelMetrics each coords)
            val pixelDimens = it.pixelMetrics each metrics

            model.toIdentity()
            model.translate(pixelCoords.x, -pixelCoords.y)
            model.scale(pixelDimens.x, pixelDimens.y)
            // model.translate(origin.x, origin.y) // Needed?
            model.rotate(angle)
            // model.translate(-origin.x, -origin.y) // Needed?
        }
    }

    protected abstract fun updateVertices ()

    protected fun updateBuffer (fromEach: Int, vararg dataSeq: FloatArray) {
        val size = dataSeq.size * dataSeq[0].size
        // println(FloatArray(size) { dataSeq[(it / 2) % dataSeq.size][(it / 2) + (it % 2) - (it / 2) % dataSeq.size] })
        val arr = FloatArray(size) { dataSeq[(it / 2) % dataSeq.size][(it / 2) + (it % 2) - (it / 2) % dataSeq.size] }
        buffer.fillDynamic(arr)
    }


    override fun draw () {
        println("Camera: ${Camera.UI}")
        println("Model: $model")

        Script.setCamera(Camera.UI)
        Script.setModel(model)

        Script.setMaterial(material)
        Script.setAmbient(ambient)

        buffer.bind()
    }

    override fun delete () = buffer.delete()
}