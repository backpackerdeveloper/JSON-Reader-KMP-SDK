package com.backpackerdevs.jsonreader

import com.backpackerdevs.jsonreader.JsonReader

/**
 * Factory for creating JsonReader instances
 * 
 * This factory provides a platform-independent way to create platform-specific
 * implementations of the JsonReader interface.
 * 
 * Usage:
 * ```
 * // Get a platform-specific JsonReader implementation
 * val jsonReader = JsonReaderFactory.create()
 * 
 * // Use the JsonReader
 * jsonReader.readJsonFromFile("sample.json")
 * ```
 * 
 * Note: On Android, you need to initialize the JsonReaderImpl before using:
 * ```
 * // In your Application class
 * JsonReaderImpl.initialize(this)
 * ```
 */
expect object JsonReaderFactory {
    /**
     * Creates a new instance of JsonReader
     * 
     * @return Platform-specific implementation of JsonReader
     */
    fun create(): JsonReader
} 