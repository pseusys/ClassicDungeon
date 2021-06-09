package com.ekdorn.classicdungeon.shared.ui

import com.ekdorn.classicdungeon.shared.Game
import com.ekdorn.classicdungeon.shared.maths.Matrix4x4
import com.ekdorn.classicdungeon.shared.maths.Vector2D
import com.ekdorn.classicdungeon.shared.maths.Vector4D


internal open class Widget (var parent: Layout? = null) {
    private var exists: Boolean = true
    private var alive: Boolean = true
    var active: Boolean = true
        get() = field && (parent?.active == true)
    var visible: Boolean = true
        get() = field && (parent?.visible == true)

    val coords = Vector2D()
    var width = 0.0
        get() = field * scale.x
    var height = 0.0
        get() = field * scale.y

    private val speed = Vector2D()
    private val acceleration = Vector2D()
    private var angle = 0.0
    private val angleSpeed = 0.0

    private val scale = Vector2D(1.0, 1.0)
    private val origin = Vector2D()
    private val model = Matrix4x4()

    private val ambient = Vector4D(1.0, 1.0, 1.0, 1.0)
    private val material = Vector4D()

    constructor (x: Double, y: Double, w: Double, h: Double): this() {
        coords.x = x
        coords.y = y
        width = w
        height = h
    }



    inline var center: Vector2D
        get () = Vector2D(coords.x + width / 2, coords.y + height / 2)
        set (v) {
            coords.x = v.x - width / 2
            coords.y = v.y - height / 2
        }

    inline var alpha: Double
        get () = material.a + ambient.a
        set (v) {
            material.a = v
            ambient.a = 0.0
        }



    open fun update () {
        /*var delta = (GameMath.speed( speed.x, acceleration.x ) - speed.x) / 2
        speed.x += delta
        coords.x += speed.x * Game.elapsed
        speed.x += delta

        delta = (GameMath.speed( speed.y, acceleration.y ) - speed.y) / 2;
        speed.y += delta
        coords.y += speed.y * Game.elapsed
        speed.y += delta

        angle += angleSpeed * Game.elapsed;*/
    }

    open fun draw () {}

    fun updateMatrix() {
        /*Matrix.setIdentity( matrix );
        Matrix.translate( matrix, x, y );
        Matrix.translate( matrix, origin.x, origin.y );
        if (angle != 0) {
            Matrix.rotate( matrix, angle );
        }
        if (scale.x != 1 || scale.y != 1) {
            Matrix.scale( matrix, scale.x, scale.y );
        }
        Matrix.translate( matrix, -origin.x, -origin.y );*/
    }



    fun detach () {
        parent = null
    }
    fun attach (root: Layout) {
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
}
