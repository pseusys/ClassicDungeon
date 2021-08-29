import com.ekdorn.classicdungeon.shared.Input
import com.ekdorn.classicdungeon.shared.Lifecycle
import com.ekdorn.classicdungeon.shared.gl.wrapper.GLFunctions.context
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.get


val surface = document.getElementById("surface") as HTMLCanvasElement

var timerEnabled = false


fun main () {
    window.onload = {
        context = surface.getContext("webgl") as WebGLRenderingContext
        surface.width = window.innerWidth
        surface.height = window.innerHeight

        // println("onstart")
        Lifecycle.scope.launch {
            Lifecycle.start(surface.width, surface.height)
        }.invokeOnCompletion {
            if ((it == null) || (it is CancellationException)) resume()
            else window.alert("Game resources incomplete!\nTry reloading page or contact developer.\n${it.message}")
        }
    }

    window.onresize = {
        pause()
        surface.width = window.innerWidth
        surface.height = window.innerHeight
        Input.onResized(surface.width, surface.height)
        resume()
    }

    window.onunload = {
        pause()
        Lifecycle.end()
        // println("onend")
    }

    document.addEventListener("visibilitychange", {
        if (document["hidden"] as Boolean) pause()
        else resume()
    })
}

fun resume () {
    timerEnabled = true
    Lifecycle.resume()
    update()
    // println("onresume")
}

fun update () {
    Lifecycle.update()
    // println("onupdate")
    if (timerEnabled) window.requestAnimationFrame { update() }
}

fun pause () {
    timerEnabled = false
    Lifecycle.pause()
    // println("onpause")
}
