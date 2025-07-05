package com.backpackerdevs.jsonreader.domain.model

/**
 * Represents the result of a JSON reading operation
 * 
 * This sealed class provides a type-safe way to handle the different states
 * of a JSON reading operation:
 * - Loading: The operation is in progress
 * - Success: The operation completed successfully
 * - Error: The operation failed
 * 
 * @param T The type of data returned in case of success
 */
sealed class JsonResult<out T> {
    /**
     * Represents a successful operation with data
     * 
     * @param data The data returned by the operation
     */
    data class Success<T>(val data: T) : JsonResult<T>()
    
    /**
     * Represents a failed operation
     * 
     * @param message A human-readable error message
     * @param exception The exception that caused the failure, if any
     */
    data class Error(val message: String, val exception: Throwable? = null) : JsonResult<Nothing>()
    
    /**
     * Represents an operation in progress
     */
    data object Loading : JsonResult<Nothing>()
} 