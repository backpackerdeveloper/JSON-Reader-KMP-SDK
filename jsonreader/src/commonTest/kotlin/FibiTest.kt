package com.backpackerdevs.jsonreader

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class JsonReaderTest {

    @Test
    fun testPlatformName() {
        // Simple test to verify platform name is not empty
        assertNotEquals("", platformName)
    }
}