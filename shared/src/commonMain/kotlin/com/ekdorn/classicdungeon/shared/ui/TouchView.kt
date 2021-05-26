package com.ekdorn.classicdungeon.shared.ui

abstract class TouchView {
    companion object {
        private const val CLICK = 1.0
        private const val LONG_CLICK = 3.0
    }

    abstract fun onClick ()
    abstract fun onLongClick ()

    // needed??
    abstract fun onTouchUp ()
    abstract fun onTouchDown ()

    abstract fun onMove ()
    abstract fun onZoom ()
    abstract fun onBackPressed ()
}
