package com.backpackerdevs.jsonreader.di

import android.content.Context
import com.backpackerdevs.jsonreader.data.repository.AndroidFileReader
import com.backpackerdevs.jsonreader.data.repository.AndroidJsonRepositoryImpl
import com.backpackerdevs.jsonreader.domain.repository.JsonRepository
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Android-specific Koin module
 */
actual fun platformModule(): Module = module {
    // Android file reader
    factory { AndroidFileReader(get()) }
    
    // Repository implementation
    single<JsonRepository> { AndroidJsonRepositoryImpl(get(), get()) }
}

/**
 * Initialize Koin with Android context
 */
fun initKoin(context: Context) {
    org.koin.core.context.startKoin {
        modules(
            commonModule(),
            platformModule(),
            module { 
                single { context.applicationContext }
            }
        )
    }
} 