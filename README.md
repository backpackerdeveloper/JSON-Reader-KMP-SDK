# KMP JSON Reader

A lightweight, cross-platform Kotlin Multiplatform library for reading and parsing JSON files on Android and iOS platforms.

[![](https://jitpack.io/v/backpackerdeveloper/JSON-Reader-KMP-SDK.svg)](https://jitpack.io/#backpackerdeveloper/JSON-Reader-KMP-SDK)

## Overview

KMP JSON Reader provides a simple, consistent API for working with JSON files across platforms while handling all the platform-specific implementation details internally. The library follows clean architecture principles with proper separation of concerns.

## Features

- **Cross-Platform Support**: Works seamlessly on both Android and iOS
- **Multiple Source Support**: 
  - Android: Read from assets or file system
  - iOS: Read from bundle resources or file system
- **Flexible Parsing**:
  - Parse JSON to Map/List structures
  - Parse JSON to specific types (Android only)
- **Reactive API**: Uses Kotlin Flow for asynchronous operations
- **Robust Error Handling**: Comprehensive error states and exception handling
- **Clean Architecture**: Well-structured code with separation of concerns

## Installation

### Gradle Setup

Add JitPack repository to your project's build file:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        // Other repositories...
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency:

```kotlin
// build.gradle.kts
dependencies {
    // For multiplatform projects
    implementation("com.github.backpackerdeveloper.JSON-Reader-KMP-SDK:jsonreader:v1.0.0")
    
    // For Android-only projects
    implementation("com.github.backpackerdeveloper.JSON-Reader-KMP-SDK:jsonreader-android:v1.0.0")
}
```

## Setup

### Android Setup

Initialize the JsonReader in your Application class:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize the JsonReader with the application context
        JsonReaderImpl.initialize(this)
    }
}
```

Don't forget to register your Application class in AndroidManifest.xml:

```xml
<application
    android:name=".MyApplication"
    ...>
    <!-- Your activities and other components -->
</application>
```

### iOS Setup

No additional setup is required for iOS.

## Basic Usage

### Creating a JsonReader Instance

```kotlin
// Get a platform-specific implementation
val jsonReader = JsonReaderFactory.create()
```

### Reading a JSON File

```kotlin
// In a CoroutineScope
lifecycleScope.launch {
    // Read a JSON file (works on both Android and iOS)
    jsonReader.readJsonFromFile("sample.json")
        .collect { result ->
            when (result) {
                is JsonResult.Loading -> {
                    // Show loading indicator
                    progressBar.isVisible = true
                }
                is JsonResult.Success -> {
                    // Hide loading indicator
                    progressBar.isVisible = false
                    
                    // Get the JSON content
                    val jsonContent = result.data
                    
                    // Parse and use the JSON
                    val parsedJson = jsonReader.parseJson(jsonContent)
                    displayJson(parsedJson)
                }
                is JsonResult.Error -> {
                    // Hide loading indicator
                    progressBar.isVisible = false
                    
                    // Show error message
                    showError(result.message)
                }
            }
        }
}
```

### Parsing JSON to a Map

```kotlin
val jsonString = """
{
    "name": "John Doe",
    "age": 30,
    "email": "john.doe@example.com",
    "isSubscribed": true,
    "address": {
        "street": "123 Main St",
        "city": "Anytown",
        "zipCode": "12345"
    },
    "phoneNumbers": [
        "555-1234",
        "555-5678"
    ]
}
"""

try {
    val jsonMap = jsonReader.parseJson(jsonString)
    
    // Access simple values
    val name = jsonMap["name"] as String
    val age = jsonMap["age"] as Int
    val isSubscribed = jsonMap["isSubscribed"] as Boolean
    
    // Access nested objects
    val address = jsonMap["address"] as Map<String, Any?>
    val street = address["street"] as String
    val city = address["city"] as String
    
    // Access arrays
    val phoneNumbers = jsonMap["phoneNumbers"] as List<String>
    val primaryPhone = phoneNumbers[0]
} catch (e: Exception) {
    // Handle parsing errors
    showError("Failed to parse JSON: ${e.message}")
}
```

### Parsing JSON to a Specific Type (Android Only)

```kotlin
// Define your data classes
data class Address(
    val street: String,
    val city: String,
    val zipCode: String
)

data class Person(
    val name: String,
    val age: Int,
    val email: String,
    val isSubscribed: Boolean,
    val address: Address,
    val phoneNumbers: List<String>
)

// JSON string
val jsonString = """
{
    "name": "John Doe",
    "age": 30,
    "email": "john.doe@example.com",
    "isSubscribed": true,
    "address": {
        "street": "123 Main St",
        "city": "Anytown",
        "zipCode": "12345"
    },
    "phoneNumbers": [
        "555-1234",
        "555-5678"
    ]
}
"""

try {
    // Parse JSON to a specific type
    val person = jsonReader.parseJsonToType(jsonString, "com.example.Person") as Person
    
    // Use the strongly-typed object
    textView.text = "Name: ${person.name}, Age: ${person.age}"
    addressView.text = "Address: ${person.address.street}, ${person.address.city}"
} catch (e: Exception) {
    // Handle parsing errors
    showError("Failed to parse JSON to Person: ${e.message}")
}
```

## Advanced Usage

### Working with Files in Different Locations

The SDK automatically handles file resolution for different locations:

```kotlin
// Android: Read from assets
jsonReader.readJsonFromFile("data/config.json")

// Android: Read from file system (absolute path)
jsonReader.readJsonFromFile("/data/user/0/com.example.app/files/config.json")

// iOS: Read from bundle
jsonReader.readJsonFromFile("config.json")

// iOS: Read from documents directory
jsonReader.readJsonFromFile("config.json") // Will find it in documents if not in bundle
```

## API Reference

### JsonReader Interface

```kotlin
interface JsonReader {
    /**
     * Read a JSON file from the given path
     * @param path Path to the JSON file
     * @return Flow emitting the JSON content with loading and error states
     */
    fun readJsonFromFile(path: String): Flow<JsonResult<String>>

    /**
     * Parse a JSON string into a Map
     * @param jsonString JSON string to parse
     * @return Map representation of the JSON
     * @throws Exception if the JSON cannot be parsed
     */
    fun parseJson(jsonString: String): Map<String, Any?>

    /**
     * Parse a JSON string into a specific type (for Android only)
     * @param jsonString JSON string to parse
     * @param typeName The fully qualified name of the target class
     * @return Object of the specified type as Any
     * @throws UnsupportedOperationException on platforms that don't support this operation
     */
    fun parseJsonToType(jsonString: String, typeName: String): Any
}
```

### JsonResult Class

```kotlin
sealed class JsonResult<out T> {
    /**
     * Represents a successful operation with data
     */
    data class Success<T>(val data: T) : JsonResult<T>()
    
    /**
     * Represents a failed operation
     */
    data class Error(val message: String, val exception: Throwable? = null) : JsonResult<Nothing>()
    
    /**
     * Represents an operation in progress
     */
    data object Loading : JsonResult<Nothing>()
}
```

## Platform-Specific Details

### Android

- Files can be read from the assets directory or the file system
- JSON can be parsed to specific types using reflection (via Gson)
- Requires initialization with Application context

### iOS

- Files can be read from the main bundle or the file system
- The `parseJsonToType` method is not supported on iOS and will throw an `UnsupportedOperationException`
- No additional initialization required

## License

This library is licensed under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.