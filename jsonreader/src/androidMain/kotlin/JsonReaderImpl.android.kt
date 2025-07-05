package com.backpackerdevs.jsonreader

import android.app.Application
import android.content.Context
import com.backpackerdevs.jsonreader.data.repository.AndroidFileReader
import com.backpackerdevs.jsonreader.data.repository.AndroidJsonRepositoryImpl
import com.backpackerdevs.jsonreader.domain.model.JsonResult
import com.backpackerdevs.jsonreader.domain.usecase.ParseJsonToTypeUseCase
import com.backpackerdevs.jsonreader.domain.usecase.ParseJsonUseCase
import com.backpackerdevs.jsonreader.domain.usecase.ReadJsonUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

/**
 * Android implementation of JsonReader
 * 
 * This implementation provides:
 * - JSON file reading from assets and file system
 * - JSON parsing to Map
 * - JSON parsing to specific types (using reflection)
 * 
 * Note: The implementation requires initialization with an Application context
 * before use via the [initialize] method.
 */
class JsonReaderImpl : JsonReader {
    private val context: Context
        get() = applicationContext ?: throw IllegalStateException(
            "Application context not initialized. Call JsonReaderImpl.initialize(application) before using."
        )
    
    private val json = Json { 
        ignoreUnknownKeys = true 
        isLenient = true
    }
    
    private val fileReader by lazy { AndroidFileReader(context) }
    private val repository by lazy { AndroidJsonRepositoryImpl(json, fileReader) }
    private val readJsonUseCase by lazy { ReadJsonUseCase(repository) }
    private val parseJsonUseCase by lazy { ParseJsonUseCase(repository) }
    private val parseJsonToTypeUseCase by lazy { ParseJsonToTypeUseCase(repository) }

    /**
     * Read a JSON file from the given path
     * 
     * @param path Path to the JSON file (can be in assets or file system)
     * @return Flow emitting the JSON content
     */
    override fun readJsonFromFile(path: String): Flow<JsonResult<String>> {
        return readJsonUseCase(path)
    }

    /**
     * Parse a JSON string into a Map
     * 
     * @param jsonString JSON string to parse
     * @return Map representation of the JSON
     */
    override fun parseJson(jsonString: String): Map<String, Any?> {
        return parseJsonUseCase(jsonString)
    }

    /**
     * Parse a JSON string into a specific type using reflection
     * 
     * @param jsonString JSON string to parse
     * @param typeName The fully qualified name of the target class
     * @return Object of the specified type as Any
     * @throws ClassNotFoundException if the class cannot be found
     * @throws IllegalArgumentException if the JSON cannot be parsed to the specified type
     */
    override fun parseJsonToType(jsonString: String, typeName: String): Any {
        return parseJsonToTypeUseCase(jsonString, typeName)
    }
    
    companion object {
        private var applicationContext: Context? = null
        
        /**
         * Initialize the JsonReader with an Application context
         * 
         * This method must be called before using the JsonReader
         * 
         * @param application The Application instance
         */
        fun initialize(application: Application) {
            applicationContext = application.applicationContext
        }
    }
} 