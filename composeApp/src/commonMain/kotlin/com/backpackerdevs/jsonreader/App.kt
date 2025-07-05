package com.backpackerdevs.jsonreader

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.backpackerdevs.jsonreader.ui.JsonReaderScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        // Use our JsonReaderScreen
        JsonReaderScreen()
    }
}