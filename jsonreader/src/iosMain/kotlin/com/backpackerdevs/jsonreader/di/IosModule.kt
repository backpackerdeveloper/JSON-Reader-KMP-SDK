package com.backpackerdevs.jsonreader.di

import com.backpackerdevs.jsonreader.data.repository.IosFileReader
import com.backpackerdevs.jsonreader.data.repository.IosJsonRepositoryImpl
import com.backpackerdevs.jsonreader.domain.repository.JsonRepository
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * iOS-specific Koin module
 */
actual fun platformModule(): Module = module {
    // iOS file reader
    factory { IosFileReader() }
    
    // Repository implementation
    single<JsonRepository> { IosJsonRepositoryImpl(get(), get()) }
}

/**
 * Initialize Koin for iOS
 */
fun initKoin() {
    org.koin.core.context.startKoin {
        modules(
            commonModule(),
            platformModule()
        )
    }
} 