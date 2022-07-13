package com.ekdorn.classicdungeon.shared.engine.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer


inline fun <reified Decodeable> Json.decodeDefault(data: Any?, default: Decodeable): Decodeable {
    val string = data as String?
    return if (string != null) decodeFromString(serializersModule.serializer(), string) else default
}
