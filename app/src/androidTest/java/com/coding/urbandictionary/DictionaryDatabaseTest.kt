package com.coding.urbandictionary

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coding.urbandictionary.db.DictionaryDao
import com.coding.urbandictionary.db.DictionaryDatabase
import com.coding.urbandictionary.model.Word
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DictionaryDatabaseTest {

    private lateinit var userDao: DictionaryDao
    private lateinit var db: DictionaryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DictionaryDatabase::class.java).build()
        userDao = db.getDictionaryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertUserTest() {
        val word = Word(id = 1, word = "Test", definition = "this is that", thumbs_up = 1, thumbs_down = 2)
        val wordId = db.getDictionaryDao().insertWordTest(word)
        val userFromDb = db.getDictionaryDao().getAllWordsListTest().get(0)
        assertEquals(userFromDb?.word, word.word)
    }
}
