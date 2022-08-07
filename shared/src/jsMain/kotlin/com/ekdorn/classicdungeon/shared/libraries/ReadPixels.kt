package com.ekdorn.classicdungeon.shared.libraries

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response
import kotlin.js.Promise


external interface Data {
    var data: Promise<Response>
    var debug: Boolean
}

external interface PNG {
    var height: Int
    var width: Int
    var pixels: ByteArray
}

@JsModule("read-pixels")
@JsNonModule
private external fun readPixels (data: Data): Promise<PNG>

suspend fun getPixels (resource: String) = readPixels(object : Data {
    override var data: Promise<Response> = window.fetch(resource)
    override var debug: Boolean = false
}).await()
