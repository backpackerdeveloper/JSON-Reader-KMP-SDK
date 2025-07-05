# KMP JSON Reader - Project Structure

This repository contains the development workspace for the KMP JSON Reader SDK.

## Project Overview

KMP JSON Reader is a Kotlin Multiplatform library for reading and parsing JSON files across Android and iOS platforms. The project follows Clean Architecture principles with a clear separation of concerns.

## Repository Structure

- `/jsonreader` - The main SDK module
- `/composeApp` - Sample Compose Multiplatform application demonstrating SDK usage
- `/iosApp` - Sample iOS application demonstrating SDK usage

## Development Setup

### Prerequisites

- Android Studio Arctic Fox or newer
- Xcode 14.0 or newer (for iOS development)
- JDK 17 or newer
- Kotlin 2.0.0 or newer

### Building the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/backpackerdeveloper/JSON-Reader-KMP-SDK.git
   cd JSON-Reader-KMP-SDK
   ```

2. Open the project in Android Studio:
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned repository and click "Open"

3. Build the project:
   ```bash
   ./gradlew build
   ```

### Running the Sample Apps

#### Android

```bash
./gradlew composeApp:installDebug
```

#### iOS

1. Open the Xcode project:
   ```bash
   open iosApp/iosApp.xcodeproj
   ```

2. Select a simulator or device and click "Run"

## Publishing the SDK

The SDK is published to JitPack. To create a new release:

1. Update the version in `jsonreader/build.gradle.kts`
2. Commit and push your changes
3. Create a new release on GitHub with a tag matching the version (e.g., `v1.0.0`)
4. JitPack will automatically build and publish the new version

## Development Guidelines

### Code Style

- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add KDoc comments to public APIs
- Keep functions small and focused

### Git Workflow

1. Create a feature branch from `main`
2. Make your changes
3. Write tests for your changes
4. Submit a pull request to `main`

### Testing

- Write unit tests for all new functionality
- Ensure tests pass on both Android and iOS platforms
- Test with different JSON structures and edge cases

## License

This project is licensed under the MIT License - see the LICENSE file for details. 