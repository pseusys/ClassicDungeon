package com.ekdorn.classicdungeon.shared.engine.generics


/**
 * This interface represents a cloneable instance (e.g. particle, image, mob, etc.).
 * TODO: revise functionality + use cases.
 */
interface Cloneable <This> {
    fun clone (): This
}
