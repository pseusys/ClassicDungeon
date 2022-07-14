package com.ekdorn.classicdungeon.shared.engine.ui

import com.ekdorn.classicdungeon.shared.engine.atomic.Vector

internal class ButtonUI: LayoutUI() {
    //var image = initializer.getOrElse("image") { null } as String?

    //var imageAlignment = ALIGNMENT.valueOf(initializer.getOrElse("imageAlignment") { ALIGNMENT.CENTER.name } as String)

    //var text = initializer.getOrElse("text") { null } as String?


    init {
        //if (image != null) add("image", ClipUI(mapOf("texture" to image)))
        //if (text != null) add("text", TextUI(mapOf("text" to text)))
    }


    override fun add (id: String, element: WidgetUI) {
        if ((id == "image" && element !is ImageUI) || (id == "text") && element !is TextUI) return
        else super.add(id, element)
    }
}
