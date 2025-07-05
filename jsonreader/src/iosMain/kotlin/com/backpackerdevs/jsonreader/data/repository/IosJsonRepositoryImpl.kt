package com.backpackerdevs.jsonreader.data.repository

import com.backpackerdevs.jsonreader.domain.repository.JsonRepository
import kotlinx.serialization.json.Json

/**
 * iOS implementation of JsonRepository
 * 
 * This class extends the common JsonRepositoryImpl and overrides the
 * parseJsonToType method to throw an UnsupportedOperationException,
 * as this functionality is not supported on iOS.
 */
class IosJsonRepositoryImpl(
    private val json: Json,
    private val fileReader: IosFileReader
) : JsonRepositoryImpl(json, fileReader), JsonRepository {
    
    /**
     * Parse a JSON string into a specific type
     * 
     * This operation is not supported on iOS.
     * 
     * @param jsonString JSON string to parse
     * @param typeName The fully qualified name of the target class
     * @return Never returns as it always throws an exception
     * @throws UnsupportedOperationException always, as this operation is not supported on iOS
     */
    override fun parseJsonToType(jsonString: String, typeName: String): Any {
        throw UnsupportedOperationException("parseJsonToType is not supported on iOS")
    }
} 