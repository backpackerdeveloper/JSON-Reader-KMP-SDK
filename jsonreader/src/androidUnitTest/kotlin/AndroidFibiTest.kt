package com.backpackerdevs.jsonreader

import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidJsonReaderTest {

    @Test
    fun testPlatformNameIsAndroid() {
        assertEquals("Android", platformName)
    }
}