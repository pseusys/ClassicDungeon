package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.dependant.gl.GLBuffer
import com.ekdorn.classicdungeon.shared.engine.glextensions.Camera
import com.ekdorn.classicdungeon.shared.engine.glextensions.Script
import com.ekdorn.classicdungeon.shared.engine.maths.Color
import com.ekdorn.classicdungeon.shared.engine.maths.Matrix
import com.ekdorn.classicdungeon.shared.engine.maths.Vector


internal abstract class WidgetUI (initializer: Map<String, *>) {
    enum class ALIGNMENT {
        START, CENTER, END
    }



    @Target(AnnotationTarget.PROPERTY)
    @Retention(AnnotationRetention.SOURCE)
    @MustBeDocumented
    annotation class Implicit



    /*
    var exists: Boolean = true
    private var alive: Boolean = true
    var active: Boolean = true
        get() = field && if (parent != null) (parent!!.active) else true
    */
    @Implicit var parent: LayoutUI? = null
    @Implicit protected var coords = Vector()
    @Implicit protected var metrics = Vector()

    @Implicit protected var dirty = false

    @Implicit private val buffer = GLBuffer(GLBuffer.TYPE.COMMON)


    @Implicit open var dimens = Vector()
        protected set

    var anchor = Vector()
    var pixelation = 1F
    var visible: Boolean = true
        // get() = field && if (parent != null) (parent!!.visible) else true

    var verticalAlignment = ALIGNMENT.CENTER
    var horizontalAlignment = ALIGNMENT.CENTER

    var speed = Vector()
    var acceleration = Vector()
    var angle = 0F
    var angleSpeed = 0F

    // var origin = Vector()
    var model = Matrix()

    var ambient = Color()
    var material = Color(1F, 1F, 1F, 1F)


    init {
        anchor = initializer.getOrElse("anchor") { anchor } as Vector
    }


    /*
    inline var center: Vector
        get () = Vector(anchor.x + metrics.x / 2, anchor.y + metrics.y / 2)
        set (v) {
            anchor.x = v.x - metrics.x / 2
            anchor.y = v.y - metrics.y / 2
        }
     */

    inline var alpha: Float
        get () = material.a + ambient.a
        set (v) {
            material.a = v
            ambient.a = 0F
        }



    protected fun parentMetrics () = parent?.metrics
    protected fun parentCoords () = parent?.coords

    private fun move (speed: Float, acceleration: Float, time: Int) = speed + acceleration * time



    open fun update (elapsed: Int) {
        if (dirty) updateVertices()

        val halfDeltaX = (move(speed.x, acceleration.x, elapsed) - speed.x) / 2
        speed.x += halfDeltaX
        anchor.x += speed.x * elapsed
        speed.x += halfDeltaX

        val halfDeltaY = (move(speed.y, acceleration.y, elapsed) - speed.y) / 2
        speed.y += halfDeltaY
        anchor.y += speed.y * elapsed
        speed.y += halfDeltaY

        angle += angleSpeed * elapsed

        model.toIdentity()
        model.translate(coords.x, -coords.y)
        model.scale(metrics.x, metrics.y)
        // model.translate(origin.x, origin.y) // Needed?
        model.rotate(angle)
        // model.translate(-origin.x, -origin.y) // Needed?
    }

    open fun draw () {
        // println("Camera: ${Camera.UI}")
        // println("Model: $model")

        Script.setCamera(Camera.UI)
        Script.setModel(model)

        Script.setMaterial(material)
        Script.setAmbient(ambient)

        buffer.bind()
    }



    open fun translate (parentCoords: Vector, parentMetrics: Vector) {
        coords = parentCoords + Vector(when (horizontalAlignment) {
            ALIGNMENT.START -> parentMetrics.x * anchor.x
            ALIGNMENT.CENTER -> parentMetrics.x * anchor.x - metrics.x / 2
            ALIGNMENT.END -> parentMetrics.x * anchor.x - metrics.x
        }, when (verticalAlignment) {
            ALIGNMENT.START -> parentMetrics.y * anchor.y
            ALIGNMENT.CENTER -> parentMetrics.y * anchor.y - metrics.y / 2
            ALIGNMENT.END -> parentMetrics.y * anchor.y - metrics.y
        })
    }

    protected open fun updateVertices () { dirty = false }

    protected fun updateBuffer (fromEach: Int, vararg dataSeq: FloatArray) {
        val size = dataSeq.size * dataSeq[0].size
        // println(FloatArray(size) { dataSeq[(it / 2) % dataSeq.size][(it / 2) + (it % 2) - (it / 2) % dataSeq.size] })
        val arr = FloatArray(size) { dataSeq[(it / 2) % dataSeq.size][(it / 2) + (it % 2) - (it / 2) % dataSeq.size] }
        buffer.fillDynamic(arr)
    }



    /*
    fun kill () {
        alive = false
        exists = false
    }
    fun revive () {
        alive = true
        exists = true
    }
    */

    open fun delete () = buffer.delete()



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
