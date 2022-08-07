package com.ekdorn.classicdungeon.shared.engine.cache

import com.ekdorn.classicdungeon.shared.engine.ui.*
import com.ekdorn.classicdungeon.shared.engine.utils.assert
import com.ekdorn.classicdungeon.shared.engine.utils.partition
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*


// Layout file management
// UI pixelization in camera depending on screen ratio
internal object Layout {
    private const val classField = "#type"
    private const val includeField = "#include"
    private const val overrideField = "#override"


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
        classDiscriminator = classField
    }


    private val layouts = mutableMapOf<String, JsonObject>()


    suspend fun load (vararg values: String) = values.forEach { layouts[it] = compile(pull(it)) }

    private suspend fun pull(layout: String): JsonObject {
        if (layout !in layouts) layouts[layout] = format.decodeFromString(JsonObject.serializer(), ResourceLoader.loadDataString("layouts/$layout.ui.json"))
        return layouts[layout]!!
    }

    private suspend fun compile(layout: JsonObject, override: JsonObject = JsonObject(mapOf())): JsonObject {
        if (includeField in layout) {
            val overridden = compile(JsonObject(layout - includeField), override)
            return compile(pull(layout[includeField]!!.jsonPrimitive.content), overridden)
        } else {
            val childfree = layout + override - "children"
            if ("children" !in layout && "children" !in override) return JsonObject(childfree)
            val children = mutableMapOf<String, JsonElement>()
            val overrideChildren = override["children"]?.jsonObject?.partition { it.value.jsonObject[overrideField]?.jsonPrimitive?.boolean == true }
            layout["children"]?.jsonObject?.entries?.forEach {
                val childOverride = overrideChildren?.first?.get(it.key)?.jsonObject?.minus(overrideField)
                children[it.key] = compile(it.value.jsonObject, JsonObject(childOverride ?: mapOf()))
            }
            overrideChildren?.second?.entries?.forEach { children[it.key] = it.value.jsonObject }
            return JsonObject(childfree + Pair("children", JsonObject(children)))
        }
    }

    private fun instantiate(layout: JsonObject): WidgetUI {
        val content = JsonObject(layout.minus("children"))
        val widget = format.decodeFromJsonElement(PolymorphicSerializer(WidgetUI::class), content)
        if ("children" in layout) {
            assert(widget is LayoutUI) { "Children-having class should extend 'LayoutUI', however ${widget::class.simpleName} doesn't!" }
            val children = layout["children"]!!.jsonObject.entries.associate { it.key to instantiate(it.value.jsonObject) }
            (widget as LayoutUI).populate(children)
        }
        return widget
    }

    fun summon (layout: String) = instantiate(layouts[layout]!!)
}
