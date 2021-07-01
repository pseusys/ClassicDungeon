package com.ekdorn.classicdungeon.shared.dependant.gl

expect class GLBuffer (size: Int) {
    val size: Int

    fun bind ()
    fun fill (data: FloatArray)
    fun delete ()
}