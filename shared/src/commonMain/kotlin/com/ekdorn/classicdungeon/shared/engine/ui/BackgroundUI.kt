package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.cache.Image
import com.ekdorn.classicdungeon.shared.engine.atomic.Rectangle
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.gl.extensions.Script
import com.ekdorn.classicdungeon.shared.gl.wrapper.GLTexture
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
internal class BackgroundUI: ResizableUI() {
    var scrollSpeed = Vector()

    var source = Image.DEFAULT
        set (v) {
            field = v
            texture = Image.get(field)
        }

    var horizontalPixelate = false

    var verticalPixelate = false


    @Transient var scroll = Vector()

    /**
     * Property texture - image source.
     * Fallback image by default.
     */
    @Transient private var texture = Image.get(source)
        set (v) {
            field = v
            field.wrap(GLTexture.WRAPPING.REPEAT, GLTexture.WRAPPING.REPEAT)
        }


    init { texture.wrap(GLTexture.WRAPPING.REPEAT, GLTexture.WRAPPING.REPEAT) }


    override fun draw () {
        super.draw()
        texture.bind()
        Script.setTexture(texture)
        Script.drawSingle()
        texture.release()
    }

    override fun update (elapsed: Int) {
        dirty = true
        super.update(elapsed)
    }

    override fun updateVertices() {
        super.updateVertices()

        scroll += scrollSpeed * Vector(1 / texture.image.metrics.x, 1 / texture.image.metrics.y)
        if (scroll.x > texture.image.metrics.x) scroll.x -= texture.image.metrics.x
        if (scroll.x < texture.image.metrics.x) scroll.x += texture.image.metrics.x
        if (scroll.y > texture.image.metrics.y) scroll.y -= texture.image.metrics.y
        if (scroll.y < texture.image.metrics.y) scroll.y += texture.image.metrics.y

        val ratio = if (!horizontalPixelate && !verticalPixelate) metrics / texture.image.metrics / pixelation
        else if (horizontalPixelate) Vector(1F, 1 / (texture.image.metrics.ratio * metrics.ratio))
        else Vector(texture.image.metrics.ratio * metrics.ratio, 1F)

        val delta = if (!horizontalPixelate && !verticalPixelate) ratio.copy().apply { x = -(x % 1) / 2; y = (y % 1) / 2 } else Vector()
        val frame = Rectangle(0F, 1F, ratio.x, 1 - ratio.y) + delta - scroll

        buffer.fill(Rectangle(0F, 0F, 1F, -1F), frame)
    }
}
