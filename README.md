# KMP JSON Reader

A Kotlin Multiplatform library for reading and parsing JSON files across Android and iOS platforms.

## Features

- Read JSON files from various sources:
  - Android: Assets directory and file system
  - iOS: Bundle resources and file system
- Parse JSON strings to Map structures
- Parse JSON to specific types (Android only)
- Clean architecture with separation of concerns
- Comprehensive error handling

## Installation

### Gradle Setup (build.gradle.kts)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    // For multiplatform projects
    implementation("com.backpackerdevs:jsonreader:1.0.0")
    
    // For Android-only projects
    implementation("com.backpackerdevs:jsonreader-android:1.0.0")
    
    // For iOS-only projects
    implementation("com.backpackerdevs:jsonreader-ios:1.0.0")
}
```

## Setup

### Android

Initialize the JsonReader in your Application class:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        JsonReaderImpl.initialize(this)
    }
}
```

### iOS

No additional setup is required for iOS.

## Usage

### Creating a JsonReader Instance

```kotlin
// Get a platform-specific implementation
val jsonReader = JsonReaderFactory.create()
```

### Reading a JSON File

```kotlin
// Read a JSON file (works on both Android and iOS)
jsonReader.readJsonFromFile("sample.json")
    .collect { result ->
        when (result) {
            is JsonResult.Loading -> {
                // Show loading state
            }
            is JsonResult.Success -> {
                val jsonContent = result.data
                // Process the JSON content
            }
            is JsonResult.Error -> {
                val errorMessage = result.message
                // Handle error
            }
        }
    }
```

### Parsing JSON to a Map

```kotlin
val jsonString = """{"name": "John", "age": 30}"""
val jsonMap = jsonReader.parseJson(jsonString)

// Access values
val name = jsonMap["name"] as String
val age = jsonMap["age"] as Int
```

### Parsing JSON to a Specific Type (Android Only)

```kotlin
data class Person(val name: String, val age: Int)

val jsonString = """{"name": "John", "age": 30}"""
val person = jsonReader.parseJsonToType(jsonString, "com.example.Person") as Person
```

## API Reference

### JsonReader Interface

The main interface for the library:

```kotlin
interface JsonReader {
    fun readJsonFromFile(path: String): Flow<JsonResult<String>>
    fun parseJson(jsonString: String): Map<String, Any?>
    fun parseJsonToType(jsonString: String, typeName: String): Any
}
```

### JsonResult Class

A sealed class representing the result of a JSON operation:

```kotlin
sealed class JsonResult<out T> {
    data class Success<T>(val data: T) : JsonResult<T>()
    data class Error(val message: String, val exception: Throwable? = null) : JsonResult<Nothing>()
    data object Loading : JsonResult<Nothing>()
}
```

## Platform-Specific Details

### Android

- Files can be read from the assets directory or the file system
- JSON can be parsed to specific types using reflection (via Gson)

### iOS

- Files can be read from the main bundle or the file system
- The `parseJsonToType` method is not supported on iOS and will throw an `UnsupportedOperationException`

## License

This library is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.