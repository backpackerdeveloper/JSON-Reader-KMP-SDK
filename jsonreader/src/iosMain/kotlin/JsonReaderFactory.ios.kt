package com.backpackerdevs.jsonreader

/**
 * iOS implementation of JsonReaderFactory
 * 
 * This factory creates an iOS-specific JsonReader implementation.
 * No additional initialization is required on iOS.
 */
actual object JsonReaderFactory {
    /**
     * Creates an iOS-specific JsonReader implementation
     * 
     * @return iOS implementation of JsonReader
     */
    actual fun create(): JsonReader {
        return JsonReaderImpl()
    }
} 