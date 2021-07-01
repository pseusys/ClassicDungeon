import com.ekdorn.classicdungeon.shared.Input
import com.ekdorn.classicdungeon.shared.Lifecycle
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement


val surface = document.getElementById("surface") as HTMLCanvasElement
lateinit var context: WebGLRenderingContext

var timerEnabled = false

fun main () {
    window.onload = {
        GlobalScope.launch {
            context = surface.getContext("webgl") as WebGLRenderingContext
            Lifecycle.start(surface.width, surface.height)
            resume()
            println("onstart")
        }
    }

    window.onfocus = {
        resume()
    }

    window.onresize = {
        pause()
        Input.onResized(surface.width, surface.height)
        resume()
    }

    window.onblur = {
        pause()
    }

    window.onunload = {
        GlobalScope.launch {
            pause()
            Lifecycle.end()
            println("onend")
        }
    }
}

fun resume () {
    timerEnabled = true
    Lifecycle.resume()
    update()
    println("onresume")
}

fun update () {
    Lifecycle.update()
    println("onupdate")
    //if (timerEnabled) window.requestAnimationFrame { update() }
}

fun pause () {
    timerEnabled = false
    Lifecycle.pause()
    println("onpause")
}
