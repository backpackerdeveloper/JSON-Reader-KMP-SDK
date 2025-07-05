package com.backpackerdevs.jsonreader.domain.repository

import com.backpackerdevs.jsonreader.domain.model.JsonResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository for JSON operations
 */
interface JsonRepository {
    /**
     * Read a JSON file from the given path
     * @param path Path to the JSON file
     * @return Flow emitting the result of the operation
     */
    fun readJsonFromFile(path: String): Flow<JsonResult<String>>
    
    /**
     * Parse a JSON string into a Map
     * @param jsonString JSON string to parse
     * @return Map representation of the JSON
     */
    fun parseJson(jsonString: String): Map<String, Any?>
    
    /**
     * Parse a JSON string into a specific type
     * @param jsonString JSON string to parse
     * @param typeName The fully qualified name of the target class
     * @return Object of the specified type as Any
     */
    fun parseJsonToType(jsonString: String, typeName: String): Any
} 