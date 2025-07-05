package com.backpackerdevs.jsonreader.data.repository

/**
 * Interface for reading files
 */
interface FileReader {
    /**
     * Read a file from the specified path
     * 
     * @param path Path to the file
     * @return The content of the file as a String
     * @throws Exception if the file cannot be read
     */
    suspend fun readFile(path: String): String
} 