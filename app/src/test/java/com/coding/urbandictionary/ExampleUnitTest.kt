package com.coding.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.coding.urbandictionary.db.DictionaryDatabase
import com.coding.urbandictionary.repository.DictionaryRepository
import com.coding.urbandictionary.ui.main.DictionaryViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}