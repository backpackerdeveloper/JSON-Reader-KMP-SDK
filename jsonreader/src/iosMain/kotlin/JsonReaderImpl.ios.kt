package com.backpackerdevs.jsonreader

import com.backpackerdevs.jsonreader.data.repository.IosFileReader
import com.backpackerdevs.jsonreader.data.repository.IosJsonRepositoryImpl
import com.backpackerdevs.jsonreader.domain.model.JsonResult
import com.backpackerdevs.jsonreader.domain.usecase.ParseJsonToTypeUseCase
import com.backpackerdevs.jsonreader.domain.usecase.ParseJsonUseCase
import com.backpackerdevs.jsonreader.domain.usecase.ReadJsonUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import platform.Foundation.NSBundle
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

/**
 * iOS implementation of JsonReader
 * 
 * This implementation provides:
 * - JSON file reading from various locations (bundle, documents directory, direct paths)
 * - JSON parsing to Map
 * - Path resolution for iOS platform
 */
class JsonReaderImpl : JsonReader {
    private val json = Json { 
        ignoreUnknownKeys = true 
        isLenient = true
    }
    
    private val fileReader = IosFileReader()
    private val repository = IosJsonRepositoryImpl(json, fileReader)
    private val readJsonUseCase = ReadJsonUseCase(repository)
    private val parseJsonUseCase = ParseJsonUseCase(repository)
    private val parseJsonToTypeUseCase = ParseJsonToTypeUseCase(repository)

    override fun readJsonFromFile(path: String): Flow<JsonResult<String>> {
        // Try to resolve the path if it's a relative path
        val resolvedPath = resolvePath(path)
        return if (resolvedPath != null) {
            readJsonUseCase(resolvedPath)
        } else {
            readJsonUseCase(path)
        }
    }

    override fun parseJson(jsonString: String): Map<String, Any?> {
        return parseJsonUseCase(jsonString)
    }

    override fun parseJsonToType(jsonString: String, typeName: String): Any {
        throw UnsupportedOperationException("parseJsonToType is not supported on iOS")
    }
    
    /**
     * Resolve a relative path to an absolute path on iOS
     * 
     * This method tries to find the file in:
     * 1. Direct path (if absolute)
     * 2. Main bundle
     * 3. Documents directory
     * 
     * @param path The path to resolve
     * @return The resolved absolute path, or null if the file couldn't be found
     */
    private fun resolvePath(path: String): String? {
        // If it's already an absolute path, return it
        if (path.startsWith("/")) {
            return path
        }
        
        // Try to find the file in the main bundle
        val fileName = path.substringBeforeLast(".", "")
        val fileExtension = if (path.contains(".")) path.substringAfterLast(".") else null
        val mainBundle = NSBundle.mainBundle
        val bundlePath = mainBundle.pathForResource(fileName, fileExtension)
        if (bundlePath != null) {
            return bundlePath
        }
        
        // Try to find the file in the Documents directory
        val fileManager = NSFileManager.defaultManager
        val paths = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        )
        if (paths.isNotEmpty()) {
            val documentsDirectory = paths[0] as String
            val documentPath = "$documentsDirectory/$path"
            if (fileManager.fileExistsAtPath(documentPath)) {
                return documentPath
            }
        }
        
        // Return null if the path couldn't be resolved
        return null
    }
} 