import com.ekdorn.classicdungeon.shared.IO
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
import kotlin.math.sign


var elapsed = 0

fun main () {
    // WebGL main game widget
    val surface = document.getElementById("surface") as HTMLCanvasElement

    // Click data, for use with mousemove: x, y and button number
    var click: Triple<Int, Int, Int>? = null

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
                else -> window.alert("Game could not start for reason unknown!\nContact developer for further information.\n${it.message}\n${it.cause}")
            }
        }
    }

    window.onresize = {
        surface.width = window.innerWidth
        surface.height = window.innerHeight
        IO.onResized(surface.width, surface.height)
    }

    window.onunload = {
        pause()
        Lifecycle.end()
    }

    window.onmousedown = {
        val number = it.button.toInt()
        if (number == 0 || number == 2) {
            click = Triple(it.x.toInt(), it.y.toInt(), number)
            IO.onClickDown(it.x.toInt(), it.y.toInt(), number == 0)
        } // else browser back and forth buttons
    }

    window.onmouseup = {
        val number = it.button.toInt()
        if (number == 0 || number == 2) {
            click = null
            IO.onClickUp(it.x.toInt(), it.y.toInt(), number == 0)
        } // else browser back and forth buttons
    }

    window.oncontextmenu = {
        it.preventDefault()
    }

    window.onmousemove = { if (click != null) {
        IO.onPointerMoved(click!!.first, click!!.second, it.x.toInt(), it.y.toInt(), click!!.third == 0)
        click = Triple(it.x.toInt(), it.y.toInt(), click!!.third)
    } }

    window.onwheel = {
        IO.onZoomed(it.x.toInt(), it.y.toInt(), it.deltaY.sign.toInt())
    }

    document.addEventListener("visibilitychange", {
        if (elapsed == 0) return@addEventListener
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
    elapsed = window.requestAnimationFrame { update() }
}

fun pause () {
    window.cancelAnimationFrame(elapsed)
    Lifecycle.pause()
}
