package com.ekdorn.classicdungeon.shared.dependant.gl

expect object GLFunctions {
    fun setup ()
    fun drawElements (count: Int, indices: ByteArray)
}