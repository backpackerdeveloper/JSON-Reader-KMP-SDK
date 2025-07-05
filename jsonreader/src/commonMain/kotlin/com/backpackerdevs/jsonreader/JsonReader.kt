package com.backpackerdevs.jsonreader

import com.backpackerdevs.jsonreader.domain.model.JsonResult
import com.backpackerdevs.jsonreader.domain.usecase.ParseJsonToTypeUseCase
import com.backpackerdevs.jsonreader.domain.usecase.ParseJsonUseCase
import com.backpackerdevs.jsonreader.domain.usecase.ReadJsonUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Main API for the JsonReader library
 * 
 * This interface provides methods for:
 * - Reading JSON files from various sources
 * - Parsing JSON strings to Map structures
 * - Parsing JSON strings to specific types (platform-dependent)
 * 
 * Use [JsonReaderFactory.create] to obtain an instance of this interface.
 */
interface JsonReader {
    /**
     * Read a JSON file from the given path
     * 
     * The path can be:
     * - On Android: A path in the assets directory or a file system path
     * - On iOS: A path in the bundle, documents directory, or a file system path
     * 
     * @param path Path to the JSON file
     * @return Flow emitting the JSON content with loading and error states
     */
    fun readJsonFromFile(path: String): Flow<JsonResult<String>>

    /**
     * Parse a JSON string into a Map
     * 
     * @param jsonString JSON string to parse
     * @return Map representation of the JSON where values can be primitives, Maps, or Lists
     * @throws Exception if the JSON cannot be parsed
     */
    fun parseJson(jsonString: String): Map<String, Any?>

    /**
     * Parse a JSON string into a specific type (for Android only)
     * 
     * This is a platform-specific implementation and may not be available on all platforms.
     * On iOS, this method throws UnsupportedOperationException.
     * 
     * @param jsonString JSON string to parse
     * @param typeName The fully qualified name of the target class
     * @return Object of the specified type as Any
     * @throws UnsupportedOperationException on platforms that don't support this operation
     * @throws ClassNotFoundException if the class cannot be found
     * @throws IllegalArgumentException if the JSON cannot be parsed to the specified type
     */
    fun parseJsonToType(jsonString: String, typeName: String): Any
} 