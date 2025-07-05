package com.backpackerdevs.jsonreader.domain.usecase

import com.backpackerdevs.jsonreader.domain.repository.JsonRepository

/**
 * Use case for parsing JSON to a specific type
 */
class ParseJsonToTypeUseCase(
    private val jsonRepository: JsonRepository
) {
    /**
     * Parse JSON to a specific type
     * @param jsonString JSON string to parse
     * @param typeName The fully qualified name of the target class
     * @return Object of the specified type
     */
    operator fun invoke(jsonString: String, typeName: String): Any {
        return jsonRepository.parseJsonToType(jsonString, typeName)
    }
} 