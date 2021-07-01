package com.ekdorn.classicdungeon.shared.dependant.gl

expect class GLShader (type: TYPE) {
    enum class TYPE {
        VERTEX, FRAGMENT
    }

    fun prepare (code: String): String?
    fun delete ()
}
