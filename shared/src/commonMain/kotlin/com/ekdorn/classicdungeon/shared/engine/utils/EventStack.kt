package com.ekdorn.classicdungeon.shared.engine.utils


/**
 * Event is a broadcasting entity, anyone can subscribe on.
 */
internal class EventStack<Target>(private val mode: TriggerMode = TriggerMode.STACK) {
    /**
     * TODO: remove if not needed
     */
    enum class TriggerMode { STACK, QUEUE }

    private val listeners = mutableListOf<TListener<Target>>()


    fun add (listener: TListener<Target>) {
        if (!listeners.contains(listener)) {
            if (mode == TriggerMode.STACK) listeners.add(listener)
            else listeners.add(0, listener)
        }
    }


    fun remove (listener: TListener<Target>) = listeners.remove(listener)

    fun fire (target: Target) {
        for (listener in listeners) listener.invoke(target)
    }
}
