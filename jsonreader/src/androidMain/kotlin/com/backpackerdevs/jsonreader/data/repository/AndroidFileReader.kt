package com.backpackerdevs.jsonreader.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

/**
 * Android implementation of FileReader
 * 
 * This implementation can read files from:
 * - Android assets directory
 * - File system using absolute or relative paths
 */
class AndroidFileReader(
    private val context: Context
) : FileReader {
    
    /**
     * Read a file from the specified path
     * 
     * The method attempts to read from:
     * 1. Android assets directory
     * 2. Direct file path
     * 
     * @param path Path to the file - can be a relative path in assets or an absolute file path
     * @return The content of the file as a String
     * @throws IOException if the file cannot be read or doesn't exist
     */
    override suspend fun readFile(path: String): String = withContext(Dispatchers.IO) {
        try {
            // First try to read from assets
            try {
                return@withContext context.assets.open(path).bufferedReader().use { it.readText() }
            } catch (e: IOException) {
                // Not in assets, try reading as a file
                val file = File(path)
                if (file.exists()) {
                    return@withContext file.readText()
                } else {
                    throw IOException("File not found at path: $path")
                }
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> throw e
                else -> throw IOException("Failed to read file: ${e.message}", e)
            }
        }
    }
} 