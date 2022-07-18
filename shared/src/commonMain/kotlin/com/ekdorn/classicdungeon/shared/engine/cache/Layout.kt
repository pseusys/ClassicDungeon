package com.ekdorn.classicdungeon.shared.engine.cache

import com.ekdorn.classicdungeon.shared.engine.general.ResourceLoader
import com.ekdorn.classicdungeon.shared.engine.ui.*
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*


// Layout file management
// UI pixelization in camera depending on screen ratio
// TODO: deal with NO_LAYOUT
internal object Layout {
    private val module = SerializersModule { polymorphic(WidgetUI::class) {
        subclass(BackgroundUI::class)
        subclass(ButtonUI::class)
        subclass(ClipUI::class)
        subclass(FrameUI::class)
        subclass(ImageUI::class)
        subclass(LayoutUI::class)
        subclass(TextUI::class)
    } }

    private val format = Json {
        serializersModule = module
        classDiscriminator = "#type"
    }


    private val layouts = mutableMapOf<String, WidgetUI>()

    suspend fun load (vararg values: String) = values.forEach {
        layouts[it] = format.decodeFromString(PolymorphicSerializer(WidgetUI::class), ResourceLoader.loadDataString("layouts/$it.ui.json"))
    }

    fun summon (layout: String) = layouts[layout]!! as LayoutUI
}
