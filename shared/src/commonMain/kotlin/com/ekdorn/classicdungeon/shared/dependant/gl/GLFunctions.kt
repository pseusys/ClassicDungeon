package com.ekdorn.classicdungeon.shared.dependant.gl

expect object GLFunctions {
    fun setup (width: Int, height: Int)
    fun drawElements (count: Int, indices: ByteArray)
}