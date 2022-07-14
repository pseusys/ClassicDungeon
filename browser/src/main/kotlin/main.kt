import com.ekdorn.classicdungeon.shared.Input
import com.ekdorn.classicdungeon.shared.Lifecycle
import com.ekdorn.classicdungeon.shared.engine.general.ResourceNotFoundException
import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions.context
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.get


val surface = document.getElementById("surface") as HTMLCanvasElement

var timer = 0


fun main () {
    window.onload = {
        context = surface.getContext("webgl") as WebGLRenderingContext
        surface.width = window.innerWidth
        surface.height = window.innerHeight

        Lifecycle.scope.launch {
            Lifecycle.start(surface.width, surface.height, ::resume)
        }.invokeOnCompletion {
            when (it) { //TODO: beautiful exception handling (we will need that a lot)
                null -> return@invokeOnCompletion
                is CancellationException -> window.alert("${it.message}\n${it.cause}")
                is ResourceNotFoundException -> window.alert("Game resources incomplete!\nTry reloading page or contact developer.\n${it.message}\n${it.cause}")
                else -> window.alert("Game could not start for reason unknown!\nContact developer for further information.\n${it.cause}")
            }
        }
    }

    window.onresize = {
        surface.width = window.innerWidth
        surface.height = window.innerHeight
        Input.onResized(surface.width, surface.height)
    }

    window.onunload = {
        pause()
        Lifecycle.end()
    }

    document.addEventListener("visibilitychange", {
        if (timer == 0) return@addEventListener
        if (document["hidden"] as Boolean) pause()
        else resume()
    })
}

fun resume () {
    Lifecycle.resume()
    update()
}

fun update () {
    Lifecycle.update()
    timer = window.requestAnimationFrame { update() }
}

fun pause () {
    window.cancelAnimationFrame(timer)
    Lifecycle.pause()
}
