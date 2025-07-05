package com.backpackerdevs.jsonreader.data.repository

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.*
import platform.Foundation.NSData
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding

/**
 * Convert JsonElement to Map for iOS platform
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