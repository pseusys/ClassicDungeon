package com.ekdorn.classicdungeon.shared.generics

import com.ekdorn.classicdungeon.shared.dependant.ResourceLoader
import com.ekdorn.classicdungeon.shared.glextensions.ImageTexture

internal object TextureCache: Assigned {
    private val resources = mutableMapOf<String, ImageTexture>()

    fun get (resource: String) = resources[resource]!!

    override suspend fun gameStarted(screenWidth: Int, screenHeight: Int) {
        resources["sample"] = ImageTexture(ResourceLoader.loadImage("sample.png"))
    }

    override suspend fun gameEnded () = resources.forEach { it.value.delete() }
}
