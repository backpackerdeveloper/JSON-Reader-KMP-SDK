# KMP JSON Reader SDK

[![](https://jitpack.io/v/backpackerdeveloper/JSON-Reader-KMP-SDK.svg)](https://jitpack.io/#backpackerdeveloper/JSON-Reader-KMP-SDK)

A lightweight, cross-platform Kotlin Multiplatform library for reading and parsing JSON files on Android and iOS platforms.

## Technical Documentation

This document provides technical details about the SDK's architecture, implementation, and guidelines for developers.

## Architecture

The SDK follows Clean Architecture principles with a clear separation of concerns:

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│                 │     │                 │     │                 │
│  Presentation   │     │     Domain      │     │      Data       │
│    (API)        │◄────┤   (Use Cases)   │◄────┤  (Repository)   │
│                 │     │                 │     │                 │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

### Layers

1. **Presentation Layer (API)**
   - `JsonReader` interface - The main API for client applications
   - Platform-specific implementations (`JsonReaderImpl`)

2. **Domain Layer**
   - Use cases for specific operations:
     - `ReadJsonUseCase`
     - `ParseJsonUseCase`
     - `ParseJsonToTypeUseCase`
   - Models like `JsonResult` for representing operation states

3. **Data Layer**
   - `JsonRepository` interface and implementations
   - `FileReader` interface with platform-specific implementations
   - JSON parsing utilities

## Implementation Details

### Platform-Specific File Reading

The SDK handles file reading differently on each platform:

#### Android
- Uses `AndroidFileReader` which can read from:
  - Assets directory using `context.assets.open(path)`
  - File system using `File(path).readText()`

#### iOS
- Uses `IosFileReader` which can read from:
  - Main bundle using `NSBundle.mainBundle.pathForResource()`
  - File system using `NSString.stringWithContentsOfFile()`

### JSON Parsing

- Common parsing to Map/List structures using Kotlin Serialization
- Platform-specific parsing to types:
  - Android: Uses Gson reflection-based parsing
  - iOS: Not supported (throws `UnsupportedOperationException`)

### Dependency Injection

The SDK uses Koin for dependency injection:

- Common module with shared dependencies
- Platform-specific modules for Android and iOS
- Factory pattern for creating instances

## Code Structure

```
jsonreader/
├── src/
│   ├── commonMain/
│   │   └── kotlin/
│   │       ├── JsonReaderFactory.kt
│   │       ├── com/backpackerdevs/jsonreader/
│   │           ├── JsonReader.kt
│   │           ├── data/
│   │           │   └── repository/
│   │           │       ├── FileReader.kt
│   │           │       ├── JsonRepositoryImpl.kt
│   │           ├── di/
│   │           │   └── KoinModule.kt
│   │           ├── domain/
│   │               ├── model/
│   │               │   └── JsonResult.kt
│   │               ├── repository/
│   │               │   └── JsonRepository.kt
│   │               └── usecase/
│   │                   ├── ParseJsonToTypeUseCase.kt
│   │                   ├── ParseJsonUseCase.kt
│   │                   └── ReadJsonUseCase.kt
│   ├── androidMain/
│   │   └── kotlin/
│   │       ├── JsonReaderFactory.android.kt
│   │       ├── JsonReaderImpl.android.kt
│   │       └── com/backpackerdevs/jsonreader/
│   │           ├── data/
│   │           │   └── repository/
│   │           │       ├── AndroidFileReader.kt
│   │           │       ├── AndroidJsonRepositoryImpl.kt
│   │           │       └── JsonExtensions.android.kt
│   │           └── di/
│   │               └── AndroidModule.kt
│   └── iosMain/
│       └── kotlin/
│           ├── JsonReaderFactory.ios.kt
│           ├── JsonReaderImpl.ios.kt
│           └── com/backpackerdevs/jsonreader/
│               ├── data/
│               │   └── repository/
│               │       ├── IosFileReader.kt
│               │       ├── IosJsonRepositoryImpl.kt
│               │       └── JsonExtensions.ios.kt
│               └── di/
│                   └── IosModule.kt
```

## Error Handling

The SDK uses a comprehensive error handling approach:

1. **Exception Propagation**:
   - Platform-specific exceptions are caught and wrapped in common exceptions
   - File reading exceptions include detailed error messages

2. **Result Wrapping**:
   - All operations return results wrapped in `JsonResult` sealed class
   - States include `Loading`, `Success`, and `Error`

3. **Logging**:
   - Production builds have minimal logging for performance
   - Debug builds include detailed logs for troubleshooting

## Developer Guidelines

### Adding New Features

1. **Add to Common Layer First**:
   - Start by defining interfaces in the common module
   - Implement common logic that's platform-independent

2. **Implement Platform-Specific Code**:
   - Add platform-specific implementations in respective modules
   - Use `expect`/`actual` declarations for platform-specific behavior

3. **Update Tests**:
   - Add tests for common functionality
   - Add platform-specific tests for each implementation

### Versioning

The SDK follows semantic versioning:

- **MAJOR**: Breaking API changes
- **MINOR**: New features, backward compatible
- **PATCH**: Bug fixes, backward compatible

### Testing Guidelines

1. **Unit Tests**:
   - Test each component in isolation
   - Mock dependencies using interfaces

2. **Integration Tests**:
   - Test the interaction between components
   - Use real implementations when possible

3. **Platform-Specific Tests**:
   - Test Android-specific code with Android instrumented tests
   - Test iOS-specific code with iOS unit tests

## Performance Considerations

- **Memory Usage**: The SDK avoids loading large JSON files into memory at once
- **Threading**: File operations are performed on background threads using coroutines
- **Caching**: No built-in caching to keep the SDK lightweight and focused

## Security Considerations

- **File Access**: The SDK only reads files, no writing operations
- **Input Validation**: JSON parsing includes validation and error handling
- **Error Messages**: Error messages don't expose sensitive information

## Compatibility

- **Android**: API 24+ (Android 7.0 Nougat)
- **iOS**: iOS 14.0+
- **Kotlin**: 2.0.0+
- **Kotlin Coroutines**: 1.7.0+

## License

This library is licensed under the MIT License.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Add your changes
4. Write tests for your changes
5. Submit a pull request

Please ensure your code follows the existing style and passes all tests.

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
}
```

### Swift Package Manager (Package.swift)

```swift
dependencies: [
    .package(url: "https://github.com/backpackerdevs/jsonreader-ios", from: "1.0.0")
]
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

### Error Handling

```kotlin
jsonReader.readJsonFromFile("non_existent.json")
    .catch { exception ->
        // Handle exceptions at the flow level
        showError("Exception: ${exception.message}")
    }
    .collect { result ->
        if (result is JsonResult.Error) {
            // Handle error result
            val errorMessage = result.message
            val exception = result.exception
            
            // Log the error
            Log.e("JsonReader", "Error reading JSON: $errorMessage", exception)
            
            // Show user-friendly message
            showError("Could not load data. Please try again later.")
        }
    }
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

This library is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. 