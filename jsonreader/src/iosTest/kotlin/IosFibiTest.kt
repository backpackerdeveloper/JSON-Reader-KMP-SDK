package com.backpackerdevs.jsonreader

import kotlin.test.Test
import kotlin.test.assertEquals

class IosJsonReaderTest {

    @Test
    fun testPlatformNameIsIOS() {
        assertEquals("iOS", platformName)
    }
}