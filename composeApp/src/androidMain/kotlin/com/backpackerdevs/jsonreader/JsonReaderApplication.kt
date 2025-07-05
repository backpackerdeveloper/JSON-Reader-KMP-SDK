package com.backpackerdevs.jsonreader

import android.app.Application
import com.backpackerdevs.jsonreader.di.initKoin

class JsonReaderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Koin at application startup
        initKoin(applicationContext)
        
        // Initialize JsonReaderImpl
        JsonReaderImpl.initialize(this)
    }
} 