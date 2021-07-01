package com.ekdorn.classicdungeon.shared.dependant.gl

expect class GLUniform (program: GLProgram, name: String) {
    fun value1i (value: Int)
    fun value4f (value1: Float, value2: Float, value3: Float, value4: Float)
    fun value4m (value: FloatArray)
}