package com.backpackerdevs.jsonreader

import androidx.compose.ui.window.ComposeUIViewController
import com.backpackerdevs.jsonreader.di.initKoin

// Initialize Koin when the module is loaded
private val initKoinForIOS = run {
    initKoin()
    true
}

fun MainViewController() = ComposeUIViewController { 
    // Make sure Koin is initialized
    initKoinForIOS
    
    App() 
}