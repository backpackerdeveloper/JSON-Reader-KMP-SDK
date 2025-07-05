package com.backpackerdevs.jsonreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backpackerdevs.jsonreader.JsonReader
import com.backpackerdevs.jsonreader.domain.model.JsonResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ViewModel for the JSON reader sample
 */
class JsonReaderViewModel(private val jsonReader: JsonReader) : ViewModel() {
    private val _jsonState = MutableStateFlow<JsonState>(JsonState.Idle)
    val jsonState: StateFlow<JsonState> = _jsonState
    
    /**
     * Read a JSON file from the given path
     */
    fun readJsonFile(path: String) {
        viewModelScope.launch {
            _jsonState.value = JsonState.Loading
            try {
                jsonReader.readJsonFromFile(path)
                    .catch { e ->
                        println("JsonReaderViewModel: Error reading JSON file: ${e.message}")
                        _jsonState.value = JsonState.Error("Failed to read JSON file: ${e.message}")
                    }
                    .collectLatest { result ->
                        when (result) {
                            is JsonResult.Loading -> {
                                _jsonState.value = JsonState.Loading
                            }
                            is JsonResult.Success -> {
                                try {
                                    val parsedData = jsonReader.parseJson(result.data)
                                    _jsonState.value = JsonState.Success(result.data, parsedData)
                                } catch (e: Exception) {
                                    println("JsonReaderViewModel: Error parsing JSON: ${e.message}")
                                    _jsonState.value = JsonState.Error("Failed to parse JSON: ${e.message}")
                                }
                            }
                            is JsonResult.Error -> {
                                println("JsonReaderViewModel: Error from JsonResult: ${result.message}")
                                _jsonState.value = JsonState.Error(result.message)
                            }
                        }
                    }
            } catch (e: Exception) {
                println("JsonReaderViewModel: Unexpected error: ${e.message}")
                _jsonState.value = JsonState.Error("Unexpected error: ${e.message}")
            }
        }
    }
    
    /**
     * Parse a JSON string to a specific type (Android only)
     */
    fun <T> parseJsonToType(jsonString: String, typeName: String): T? {
        return try {
            @Suppress("UNCHECKED_CAST")
            jsonReader.parseJsonToType(jsonString, typeName) as T
        } catch (e: UnsupportedOperationException) {
            println("JsonReaderViewModel: parseJsonToType not supported: ${e.message}")
            null
        } catch (e: Exception) {
            println("JsonReaderViewModel: Error in parseJsonToType: ${e.message}")
            _jsonState.value = JsonState.Error("Failed to parse JSON: ${e.message}")
            null
        }
    }
}

/**
 * State for JSON reading operations
 */
sealed class JsonState {
    data object Idle : JsonState()
    data object Loading : JsonState()
    data class Success(val jsonString: String, val jsonData: Map<String, Any?>) : JsonState()
    data class Error(val message: String) : JsonState()
} 