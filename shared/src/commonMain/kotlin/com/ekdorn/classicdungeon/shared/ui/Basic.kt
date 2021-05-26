package com.ekdorn.classicdungeon.shared.ui



abstract class Basic (var parent: Layout? = null) {
    private var exists: Boolean = true

    private var alive: Boolean = true

    var active: Boolean = true
        get() = field && (parent?.active == true)

    var visible: Boolean = true
        get() = field && (parent?.visible == true)

    abstract fun update ()
    abstract fun draw ()

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