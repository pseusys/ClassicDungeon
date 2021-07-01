package com.ekdorn.classicdungeon.shared.dependant.gl

expect class GLAttribute (program: GLProgram, name: String) {
    fun enable ()
    fun disable ()
    fun set (size: Int, offset: Int, stride: Int)
}
