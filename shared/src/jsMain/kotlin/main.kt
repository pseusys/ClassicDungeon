import com.ekdorn.classicdungeon.shared.Lifecycle
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLCanvasElement


const val interval = 16


val surface = document.getElementById("surface") as HTMLCanvasElement
var timerEnabled = false

fun main () {
    window.onload = {
        Lifecycle.start()
        resume()
        println("onstart")
    }

    window.onfocus = {
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
    if (timerEnabled) window.setTimeout({ update() }, interval)
}

fun pause () {
    timerEnabled = false
    Lifecycle.pause()
    println("onpause")
}
