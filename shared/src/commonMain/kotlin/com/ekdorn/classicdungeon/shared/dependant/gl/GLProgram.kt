package com.ekdorn.classicdungeon.shared.dependant.gl

expect class GLProgram () {
    fun use ()
    fun delete ()
    fun attach (shader: GLShader)
    fun link (): String?
}
