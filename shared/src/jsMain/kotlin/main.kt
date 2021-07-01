import com.ekdorn.classicdungeon.shared.Lifecycle
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement


const val interval = 16


val surface = document.getElementById("surface") as HTMLCanvasElement
lateinit var context: WebGLRenderingContext

// to requestframe
var timerEnabled = false

fun main () {
    window.onload = {
        GlobalScope.launch {
            context = surface.getContext("webgl") as WebGLRenderingContext
            context.viewport(0, 0, surface.width, surface.height)
            Lifecycle.start()
            resume()
            println("onstart")
        }
    }

    window.onfocus = {
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
    //if (timerEnabled) window.setTimeout({ update() }, interval)
}

fun pause () {
    timerEnabled = false
    Lifecycle.pause()
    println("onpause")
}
