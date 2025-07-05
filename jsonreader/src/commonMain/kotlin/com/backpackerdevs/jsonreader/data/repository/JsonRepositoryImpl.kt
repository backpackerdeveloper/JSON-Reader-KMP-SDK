package com.backpackerdevs.jsonreader.data.repository

import com.backpackerdevs.jsonreader.domain.model.JsonResult
import com.backpackerdevs.jsonreader.domain.repository.JsonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

/**
 * Implementation of JsonRepository interface
 * 
 * This class provides common implementation for:
 * - Reading JSON files using the FileReader
 * - Parsing JSON strings to Map structures
 * 
 * Platform-specific implementations should extend this class and override
 * the parseJsonToType method.
 */
open class JsonRepositoryImpl(
    private val json: Json,
    private val fileReader: FileReader
) : JsonRepository {
    
    /**
     * Read a JSON file from the given path
     * 
     * @param path Path to the JSON file
     * @return Flow emitting the result of the operation
     */
    override fun readJsonFromFile(path: String): Flow<JsonResult<String>> = flow {
        emit(JsonResult.Loading)
        try {
            val content = fileReader.readFile(path)
            emit(JsonResult.Success(content))
        } catch (e: Exception) {
            emit(JsonResult.Error("Failed to read JSON file: ${e.message}", e))
        }
    }

    /**
     * Parse a JSON string into a Map
     * 
     * @param jsonString JSON string to parse
     * @return Map representation of the JSON
     * @throws Exception if the JSON cannot be parsed
     */
    override fun parseJson(jsonString: String): Map<String, Any?> {
        return try {
            @Suppress("UNCHECKED_CAST")
            json.parseToJsonElement(jsonString).toMap() as Map<String, Any?>
        } catch (e: Exception) {
            throw Exception("Failed to parse JSON: ${e.message}", e)
        }
    }

    /**
     * Parse a JSON string into a specific type
     * 
     * This method is not implemented in common code and should be
     * overridden by platform-specific implementations.
     * 
     * @param jsonString JSON string to parse
     * @param typeName The fully qualified name of the target class
     * @return Object of the specified type as Any
     * @throws NotImplementedError in the common implementation
     */
    override fun parseJsonToType(jsonString: String, typeName: String): Any {
        throw NotImplementedError("This method is not implemented in common code. Use platform-specific implementation.")
    }
}

/**
 * Extension function to convert JsonElement to Map
 * 
 * This is an expect function that should be implemented by each platform.
 */
expect fun kotlinx.serialization.json.JsonElement.toMap(): Map<String, Any?> 