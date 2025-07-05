package com.backpackerdevs.jsonreader

/**
 * Android implementation of JsonReaderFactory
 * 
 * This factory creates an Android-specific JsonReader implementation.
 * 
 * Note: Before using the created JsonReader, you must initialize it with an
 * Application context by calling JsonReaderImpl.initialize(application).
 */
actual object JsonReaderFactory {
    /**
     * Creates an Android-specific JsonReader implementation
     * 
     * @return Android implementation of JsonReader
     */
    actual fun create(): JsonReader {
        return JsonReaderImpl()
    }
} 