import Foundation

class FileHelper {
    static func copyResourceToDocuments(resourceName: String, resourceExtension: String) -> String? {
        // Get the path to the resource in the bundle
        guard let resourcePath = Bundle.main.path(forResource: resourceName, ofType: resourceExtension) else {
            print("Resource \(resourceName).\(resourceExtension) not found in bundle")
            return nil
        }
        
        // Get the documents directory
        guard let documentsDirectory = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first else {
            print("Could not access documents directory")
            return nil
        }
        
        // Create the destination URL
        let destinationURL = documentsDirectory.appendingPathComponent("\(resourceName).\(resourceExtension)")
        
        // Copy the file
        do {
            if FileManager.default.fileExists(atPath: destinationURL.path) {
                try FileManager.default.removeItem(at: destinationURL)
            }
            try FileManager.default.copyItem(atPath: resourcePath, toPath: destinationURL.path)
            print("Successfully copied \(resourceName).\(resourceExtension) to \(destinationURL.path)")
            return destinationURL.path
        } catch {
            print("Failed to copy file: \(error)")
            return nil
        }
    }
} 