package com.ekdorn.classicdungeon.shared.engine.utils

import com.ekdorn.classicdungeon.shared.engine.lib.TCListener


/**
 * Event is a broadcasting entity, anyone can subscribe on.
 * Any listener can also consume the event, returning true.
 */
internal class Event <Target> (private val mode: TriggerMode = TriggerMode.STACK) {
    /**
     * TODO: remove if not needded
     */
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
