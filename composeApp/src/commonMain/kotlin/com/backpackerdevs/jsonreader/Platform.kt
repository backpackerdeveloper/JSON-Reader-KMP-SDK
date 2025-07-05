package com.backpackerdevs.jsonreader

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform