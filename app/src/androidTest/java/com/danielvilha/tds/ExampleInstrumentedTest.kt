package com.danielvilha.tds

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danielvilha.tds.network.Builder

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.danielvilha.tds", appContext.packageName)
    }

    @Test
    fun getEmployers() {
        val employer = Builder.buildService().getEmployees()
        assertEquals("com.danielvilha.tds", employer.toString())
    }
}