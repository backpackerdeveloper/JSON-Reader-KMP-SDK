package com.backpackerdevs.jsonreader.data.repository

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

/**
 * iOS implementation of FileReader
 * 
 * This implementation can read files from:
 * - Direct file paths
 * - Main bundle resources
 */
class IosFileReader : FileReader {
    
    @OptIn(ExperimentalForeignApi::class)
    override suspend fun readFile(path: String): String {
        try {
            // First try to read as a direct file path
            val fileManager = NSFileManager.defaultManager
            if (fileManager.fileExistsAtPath(path)) {
                val contents = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null)
                if (contents != null) {
                    return contents as String
                }
                throw IOException("Failed to read file content from path: $path")
            }
            
            // Try to find the file in the main bundle
            val fileName = path.substringBeforeLast(".", "")
            val fileExtension = if (path.contains(".")) path.substringAfterLast(".") else null
            val mainBundle = NSBundle.mainBundle
            val bundlePath = mainBundle.pathForResource(fileName, fileExtension)
            
            if (bundlePath != null) {
                val contents = NSString.stringWithContentsOfFile(bundlePath, NSUTF8StringEncoding, null)
                if (contents != null) {
                    return contents as String
                }
                throw IOException("Failed to read file content from bundle path: $bundlePath")
            }
            
            throw FileNotFoundException("File not found: $path")
        } catch (e: Exception) {
            when (e) {
                is FileNotFoundException, is IOException -> throw e
                else -> throw IOException("Failed to read file: ${e.message}", e)
            }
        }
    }
    
    /**
     * Exception thrown when a file is not found
     */
    class FileNotFoundException(message: String) : Exception(message)
    
    /**
     * Exception thrown when there is an error reading a file
     */
    class IOException(message: String, cause: Throwable? = null) : Exception(message, cause)
} 