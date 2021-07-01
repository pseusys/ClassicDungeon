package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.dependant.ResourceLoader
import com.ekdorn.classicdungeon.shared.glwrapper.ImageTexture
import com.ekdorn.classicdungeon.shared.utils.Image

internal object TextureCache: Assigned {
    private val resources = mutableMapOf<String, Image>()

    fun get (resource: String) = ImageTexture(resources[resource]!!)

    override suspend fun gameStarted () {
        resources["sample"] = ResourceLoader.loadImage("sample.png")
    }

    override suspend fun gameEnded () {}
}
