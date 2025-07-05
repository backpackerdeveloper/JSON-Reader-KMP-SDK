package com.backpackerdevs.jsonreader.di

import com.backpackerdevs.jsonreader.domain.usecase.ParseJsonToTypeUseCase
import com.backpackerdevs.jsonreader.domain.usecase.ParseJsonUseCase
import com.backpackerdevs.jsonreader.domain.usecase.ReadJsonUseCase
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Common Koin module for the JSON reader
 */
fun commonModule() = module {
    // JSON serialization
    single { 
        Json { 
            ignoreUnknownKeys = true 
            isLenient = true
        } 
    }

    // Use cases
    factory { ReadJsonUseCase(get()) }
    factory { ParseJsonUseCase(get()) }
    factory { ParseJsonToTypeUseCase(get()) }
}

/**
 * Platform-specific Koin module
 */
expect fun platformModule(): Module 