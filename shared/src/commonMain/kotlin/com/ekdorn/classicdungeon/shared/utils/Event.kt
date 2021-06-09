package com.ekdorn.classicdungeon.shared.utils



internal typealias Listener <Target> = suspend (Target)->Boolean

internal class Event <Target> (private val mode: TriggerMode = TriggerMode.QUEUE) {
    enum class TriggerMode { STACK, QUEUE }

    private val listeners = mutableListOf<Listener<Target>>()


    fun add (listener: Listener<Target>) {
        if (!listeners.contains(listener)) {
            if (mode == TriggerMode.STACK) listeners.add(listener)
            else listeners.add(0, listener)
        }
    }

    fun remove (listener: Listener<Target>) = listeners.remove(listener)

    suspend fun fire (target: Target) {
        for (listener in listeners) if (listener.invoke(target)) return
    }
}
