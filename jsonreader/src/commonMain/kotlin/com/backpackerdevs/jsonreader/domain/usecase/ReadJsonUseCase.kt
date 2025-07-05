package com.backpackerdevs.jsonreader.domain.usecase

import com.backpackerdevs.jsonreader.domain.model.JsonResult
import com.backpackerdevs.jsonreader.domain.repository.JsonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for reading JSON files
 */
class ReadJsonUseCase(
    private val jsonRepository: JsonRepository
) {
    /**
     * Execute the use case to read a JSON file
     * @param path Path to the JSON file
     * @return Flow emitting the JSON string content
     */
    operator fun invoke(path: String): Flow<JsonResult<String>> {
        return jsonRepository.readJsonFromFile(path)
    }
} 