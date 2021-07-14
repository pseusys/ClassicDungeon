package com.ekdorn.classicdungeon.shared.engine.utils

import com.ekdorn.classicdungeon.shared.engine.lib.TCListener


internal class Event <Target> (private val mode: TriggerMode = TriggerMode.STACK) {
    enum class TriggerMode { STACK, QUEUE }

    private val listeners = mutableListOf<TCListener<Target>>()


    fun add (listener: TCListener<Target>) {
        if (!listeners.contains(listener)) {
            if (mode == TriggerMode.STACK) listeners.add(listener)
            else listeners.add(0, listener)
        }
    }


    fun remove (listener: TCListener<Target>) = listeners.remove(listener)

    fun fire (target: Target) {
        for (listener in listeners) if (listener.invoke(target)) return
    }
}
