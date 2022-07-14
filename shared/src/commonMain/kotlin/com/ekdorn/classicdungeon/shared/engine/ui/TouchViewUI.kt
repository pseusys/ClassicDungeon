package com.ekdorn.classicdungeon.shared.engine.ui



internal interface TouchViewUI {
    companion object {
        private const val CLICK = 1.0
        private const val LONG_CLICK = 3.0
    }

    fun onClick ()
    fun onLongClick ()

    // needed??
    fun onTouchUp ()
    fun onTouchDown ()

    fun onMove ()
    fun onZoom ()
    fun onBackPressed ()
}
