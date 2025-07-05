package com.backpackerdevs.jsonreader.data.repository

import kotlinx.serialization.json.*
import org.json.JSONObject

/**
 * Convert JsonElement to Map for Android platform
 */
actual fun JsonElement.toMap(): Map<String, Any?> {
    return when (this) {
        is JsonObject -> this.mapValues { it.value.toAny() }
        else -> throw IllegalArgumentException("Cannot convert JsonElement to Map: Not a JsonObject")
    }
}

/**
 * Convert JsonElement to appropriate native type
 */
private fun JsonElement.toAny(): Any? {
    return when (this) {
        is JsonObject -> this.toMap()
        is JsonArray -> this.map { it.toAny() }
        is JsonPrimitive -> {
            when {
                this.isString -> this.content
                this.booleanOrNull != null -> this.boolean
                this.intOrNull != null -> this.int
                this.longOrNull != null -> this.long
                this.doubleOrNull != null -> this.double
                this.floatOrNull != null -> this.float
                this.contentOrNull == "null" -> null
                else -> this.content
            }
        }
        JsonNull -> null
    }
}

/**
 * Parse JSON string to a specific type (Android implementation)
 */
fun <T> parseJsonToType(jsonString: String, clazz: Class<T>, json: Json): T {
    // For Android, we could use Gson or other libraries for more complex mapping
    // This is a simplified example
    val jsonObject = JSONObject(jsonString)
    
    @Suppress("UNCHECKED_CAST")
    return when {
        clazz == String::class.java -> jsonString as T
        clazz == Map::class.java -> {
            val map = mutableMapOf<String, Any?>()
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                map[key] = jsonObject.get(key)
            }
            map as T
        }
        else -> throw IllegalArgumentException("Unsupported class type: ${clazz.name}")
    }
} 