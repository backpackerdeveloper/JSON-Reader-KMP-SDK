import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                .onAppear {
                    // Copy the sample.json file to the documents directory
                    if let path = FileHelper.copyResourceToDocuments(resourceName: "sample", resourceExtension: "json") {
                        print("Sample JSON file copied to: \(path)")
                    } else {
                        print("Failed to copy sample JSON file!")
                    }
                }
    }
}



