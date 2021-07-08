import com.ekdorn.classicdungeon.shared.Input
import com.ekdorn.classicdungeon.shared.Lifecycle
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLImageElement


val surface = document.getElementById("surface") as HTMLCanvasElement
lateinit var context: WebGLRenderingContext


var timerEnabled = false

fun main () {
    window.onload = {
        context = surface.getContext("webgl") as WebGLRenderingContext
        surface.width = window.innerWidth
        surface.height = window.innerHeight

        println("onstart")
        MainScope().promise {
            Lifecycle.start(surface.width, surface.height)
        }.then { resume() }
    }

    window.onfocus = {
        resume()
    }

    window.onresize = {
        pause()
        surface.width = window.innerWidth
        surface.height = window.innerHeight
        Input.onResized(surface.width, surface.height)
        resume()
    }

    window.onblur = {
        pause()
    }

    window.onunload = {
        pause()
        Lifecycle.end()
        println("onend")
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
    // if (timerEnabled) window.requestAnimationFrame { update() }
}

fun pause () {
    timerEnabled = false
    Lifecycle.pause()
    println("onpause")
}
