package com.backpackerdevs.jsonreader.data.repository

import com.backpackerdevs.jsonreader.domain.repository.JsonRepository
import kotlinx.serialization.json.Json

/**
 * Android implementation of JsonRepository
 * 
 * This class extends the common JsonRepositoryImpl and implements
 * the parseJsonToType method using Gson for Android.
 */
class AndroidJsonRepositoryImpl(
    private val json: Json,
    private val fileReader: AndroidFileReader
) : JsonRepositoryImpl(json, fileReader), JsonRepository {
    
    /**
     * Parse a JSON string into a specific type using reflection
     * 
     * This implementation uses Gson to convert the JSON string to the specified type.
     * 
     * @param jsonString JSON string to parse
     * @param typeName The fully qualified name of the target class
     * @return Object of the specified type
     * @throws ClassNotFoundException if the class cannot be found
     * @throws IllegalArgumentException if the JSON cannot be parsed to the specified type
     */
    override fun parseJsonToType(jsonString: String, typeName: String): Any {
        try {
            val clazz = Class.forName(typeName)
            val gson = com.google.gson.Gson()
            return gson.fromJson(jsonString, clazz)
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("Class not found: $typeName", e)
        } catch (e: com.google.gson.JsonSyntaxException) {
            throw IllegalArgumentException("Invalid JSON syntax: ${e.message}", e)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to parse JSON to type: ${e.message}", e)
        }
    }
} 