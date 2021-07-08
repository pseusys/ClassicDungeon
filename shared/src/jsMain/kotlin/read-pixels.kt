import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response
import kotlin.js.Promise

private class Data (val data: Promise<Response>, val debug: Boolean)

class PNG (val height: Int, val width: Int, val pixels: ByteArray)

@JsModule("read-pixels")
@JsNonModule
private external fun readPixels (data: Data): Promise<PNG>

suspend fun getPixels (resource: String) = readPixels(Data(window.fetch(resource), false)).await()
