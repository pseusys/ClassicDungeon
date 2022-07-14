package com.ekdorn.classicdungeon.shared.engine.cache

import com.ekdorn.classicdungeon.shared.engine.general.ResourceLoader
import com.ekdorn.classicdungeon.shared.engine.ui.*
import com.ekdorn.classicdungeon.shared.engine.ui.BackgroundUI
import com.ekdorn.classicdungeon.shared.engine.ui.FrameUI
import com.ekdorn.classicdungeon.shared.engine.ui.LayoutUI
import com.ekdorn.classicdungeon.shared.engine.ui.WidgetUI
import kotlinx.serialization.*
import kotlinx.serialization.json.*


// Layout file management
// UI pixelization in camera depending on screen ratio
// TODO: deal with NO_LAYOUT
internal object Layout {
    private object UI {
        val UIs: Map<String, KSerializer<out WidgetUI>> = mapOf(
            "BackgroundUI" to BackgroundUI.serializer(),
            "ClipUI" to ClipUI.serializer(),
            "FrameUI" to FrameUI.serializer(),
            "ImageUI" to ImageUI.serializer(),
            "LayoutUI" to LayoutUI.serializer(),
            "TextUI" to TextUI.serializer()
        )
    }

    @Serializable
    data class WidgetHolder (val cls: String, val id: String, val value: JsonObject, val children: List<WidgetHolder>? = null) {
        fun unpack(): WidgetUI {
            val view = Json.decodeFromJsonElement(UI.UIs[cls]!!, value)
            return if (view !is LayoutUI) view
            else view.also { it.populate(children?.associate { child -> child.id to child.unpack() } ?: mapOf()) }
        }
    }


    private val layouts = mutableMapOf<String, WidgetHolder>()

    suspend fun load (vararg values: String) = values.forEach {
        layouts[it] = Json.decodeFromString(WidgetHolder.serializer(), ResourceLoader.loadDataString("layouts/$it.ui.json"))
    }

    fun summon (layout: String) = layouts[layout]!!.unpack() as LayoutUI
}
