package com.ekdorn.classicdungeon.shared.engine.ui.implementable

import com.ekdorn.classicdungeon.shared.engine.atomic.Color
import com.ekdorn.classicdungeon.shared.engine.ui.WidgetUI


/**
 * Inline property alpha - transparency of that widgets texture.
 * Zero by default.
 */
internal inline var WidgetUI.alpha: Float
    get () = material.a + ambient.a
    set (v) {
        material.a = v
        ambient.a = 0F
    }


private fun setColor(clear: Color, apply: Color, new: Color) {
    clear.apply { r = 0F; g = 0F; b = 0F; a = 0F }
    apply.apply { r = new.r; g = new.g; b = new.b; a = new.a }
}

internal fun WidgetUI.resetColor() = setColor(ambient, material, Color(0xFFFFFFFFU))

internal fun WidgetUI.multiplyColor(color: Color) = setColor(ambient, material, color)

internal fun WidgetUI.addColor(color: Color) = setColor(material, ambient, color)

internal fun WidgetUI.setBrightness(v: Float) = material.apply { r = v; g = v; b = v; a = v }
