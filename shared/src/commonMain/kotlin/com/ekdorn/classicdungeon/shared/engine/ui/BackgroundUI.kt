package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.cache.Gallery
import com.ekdorn.classicdungeon.shared.engine.atomic.Rectangle
import com.ekdorn.classicdungeon.shared.engine.atomic.Vector
import com.ekdorn.classicdungeon.shared.gl.extensions.Script
import com.ekdorn.classicdungeon.shared.gl.wrapper.GLTexture


internal class BackgroundUI (initializer: Map<String, *> = hashMapOf<String, Any>()): ResizableUI(initializer) {
    @Implicit var scroll = Vector()

    var scrollSpeed = Vector.create(initializer["scrollSpeed"] as String?, Vector())

    /**
     * Property texture - image source.
     * Fallback image by default.
     */
    var texture = Gallery.get(initializer.getOrElse("texture") { Gallery.DEFAULT } as String)
        set (v) {
            field = v
            field.wrap(GLTexture.WRAPPING.REPEAT, GLTexture.WRAPPING.REPEAT)
        }

    var horizontalPixelate = initializer.getOrElse("horizontalPixelate") { false } as Boolean

    var verticalPixelate = initializer.getOrElse("verticalPixelate") { false } as Boolean


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
