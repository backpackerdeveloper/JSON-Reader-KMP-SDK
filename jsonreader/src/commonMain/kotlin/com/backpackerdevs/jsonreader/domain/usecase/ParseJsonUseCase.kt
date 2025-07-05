package com.backpackerdevs.jsonreader.domain.usecase

import com.backpackerdevs.jsonreader.domain.repository.JsonRepository

/**
 * Use case for parsing JSON strings to Map
 */
class ParseJsonUseCase(
    private val jsonRepository: JsonRepository
) {
    /**
     * Execute the use case to parse a JSON string to a Map
     * @param jsonString The JSON string to parse
     * @return Parsed JSON as a Map
     */
    operator fun invoke(jsonString: String): Map<String, Any?> {
        return jsonRepository.parseJson(jsonString)
    }
} 