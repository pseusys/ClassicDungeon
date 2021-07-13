package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.dependant.gl.GLBuffer
import com.ekdorn.classicdungeon.shared.glextensions.Camera
import com.ekdorn.classicdungeon.shared.glextensions.Script
import com.ekdorn.classicdungeon.shared.maths.Color
import com.ekdorn.classicdungeon.shared.maths.Matrix
import com.ekdorn.classicdungeon.shared.maths.Rectangle
import com.ekdorn.classicdungeon.shared.maths.Vector

internal abstract class ElementUI (rect: Rectangle): WidgetUI(rect) {
    constructor (pos: Vector, width: Float = -1F, height: Float = -1F): this(Rectangle(pos.x, pos.y, width, height)) {
        if ((width == -1F) && (height == -1F)) metrics.apply { x = 1F; y = 1F }
        else {
            idealWidth = width
            idealHeight = height
        }
    }

    protected var pixelCoords = Vector()
    protected var pixelMetrics = Vector()

    var preserving = true

    protected var idealWidth = -1F
    protected var idealHeight = -1F

    private val speed = Vector()
    private val acceleration = Vector()
    private var angle = 0F
    private val angleSpeed = 0F

    // private val origin = Vector()
    private val model = Matrix()

    protected val ambient = Color()
    protected val material = Color(1F, 1F, 1F, 1F)

    private val buffer = GLBuffer(GLBuffer.TYPE.COMMON)


    private fun speed (speed: Float, acceleration: Float, time: Int) = speed + acceleration * time

    override fun update (elapsed: Int) {
        val halfDeltaX = (speed(speed.x, acceleration.x, elapsed) - speed.x) / 2
        speed.x += halfDeltaX
        coords.x += speed.x * elapsed
        speed.x += halfDeltaX

        val halfDeltaY = (speed(speed.y, acceleration.y, elapsed) - speed.y) / 2
        speed.y += halfDeltaY
        coords.y += speed.y * elapsed
        speed.y += halfDeltaY

        angle += angleSpeed * elapsed

        model.toIdentity()
        model.translate(pixelCoords.x, -pixelCoords.y)
        model.scale(pixelMetrics.x, pixelMetrics.y)
        // model.translate(origin.x, origin.y) // Needed?
        model.rotate(angle)
        // model.translate(-origin.x, -origin.y) // Needed?
    }


    open fun translate (parentCoords: Vector, parentMetrics: Vector) {
        pixelCoords = parentCoords + (parentMetrics each coords)
        val newMetrics = parentMetrics each metrics
        if (newMetrics != pixelMetrics) resize(parentMetrics.ratio)
        pixelMetrics = parentMetrics each metrics
    }

    abstract fun resize (ratio: Float)


    protected abstract fun updateVertices ()

    protected fun updateBuffer (fromEach: Int, vararg dataSeq: FloatArray) {
        val size = dataSeq.size * dataSeq[0].size
        // println(FloatArray(size) { dataSeq[(it / 2) % dataSeq.size][(it / 2) + (it % 2) - (it / 2) % dataSeq.size] })
        val arr = FloatArray(size) { dataSeq[(it / 2) % dataSeq.size][(it / 2) + (it % 2) - (it / 2) % dataSeq.size] }
        buffer.fillDynamic(arr)
    }


    override fun draw () {
        // println("Camera: ${Camera.UI}")
        // println("Model: $model")

        Script.setCamera(Camera.UI)
        Script.setModel(model)

        Script.setMaterial(material)
        Script.setAmbient(ambient)

        buffer.bind()
    }

    override fun delete () = buffer.delete()



    inline var alpha: Float
        get () = material.a + ambient.a
        set (v) {
            material.a = v
            ambient.a = 0F
        }

    fun resetColor () {
        material.apply { r = 1F; g = 1F; b = 1F; a = 1F }
        ambient.apply { r = 0F; g = 0F; b = 0F; a = 0F }
    }

    fun multiplyColor (color: Color) {
        ambient.apply { r = 0F; g = 0F; b = 0F }
        material.apply { r = color.r; g = color.g; b = color.b }
    }

    fun addColor (color: Color) {
        material.apply { r = 0F; g = 0F; b = 0F }
        ambient.apply { r = color.r; g = color.g; b = color.b }
    }
}
