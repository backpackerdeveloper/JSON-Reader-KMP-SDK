package com.backpackerdevs.jsonreader.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.backpackerdevs.jsonreader.JsonReaderFactory
import com.backpackerdevs.jsonreader.viewmodel.JsonReaderViewModel
import com.backpackerdevs.jsonreader.viewmodel.JsonState

@Composable
fun JsonReaderScreen() {
    val viewModel = remember { JsonReaderViewModel(JsonReaderFactory.create()) }
    val jsonState by viewModel.jsonState.collectAsState()
    
    // Simply read the sample.json file from common resources
    LaunchedEffect(Unit) {
        println("Reading sample.json from common resources")
        viewModel.readJsonFile("sample.json")
    }
    
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        when (val state = jsonState) {
            is JsonState.Idle -> {
                Text("Waiting to read JSON...")
            }
            is JsonState.Loading -> {
                CircularProgressIndicator()
            }
            is JsonState.Success -> {
                JsonContent(state.jsonData)
            }
            is JsonState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(onClick = { viewModel.readJsonFile("sample.json") }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@Composable
fun JsonContent(jsonData: Map<String, Any?>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                "JSON Content",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        
        // Display title and description
        jsonData["title"]?.let { 
            item {
                Text(
                    "Title: $it",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        
        jsonData["description"]?.let {
            item {
                Text(
                    "Description: $it",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
        
        // Display items
        @Suppress("UNCHECKED_CAST")
        val items = jsonData["items"] as? List<Map<String, Any?>> ?: emptyList()
        item {
            Text(
                "Items (${items.size}):",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(items) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "ID: ${item["id"]}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "Name: ${item["name"]}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Description: ${item["description"]}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        
        // Display metadata
        @Suppress("UNCHECKED_CAST")
        val metadata = jsonData["metadata"] as? Map<String, Any?> ?: emptyMap()
        if (metadata.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Metadata:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        metadata.forEach { (key, value) ->
                            Text(
                                "$key: $value",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
} 