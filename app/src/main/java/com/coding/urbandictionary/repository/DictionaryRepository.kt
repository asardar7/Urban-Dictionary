package com.coding.urbandictionary.repository

import com.coding.urbandictionary.api.RetrofitInstance
import com.coding.urbandictionary.db.DictionaryDatabase
import com.coding.urbandictionary.model.DictionaryList
import com.coding.urbandictionary.model.Word
import retrofit2.Retrofit
import java.util.*

class DictionaryRepository (val db: DictionaryDatabase) {

    suspend fun getWord(word: String) =
        RetrofitInstance.api.getSearchData(word)

    suspend fun insert(word: Word) = db.getDictionaryDao().insertWord(word)

    suspend fun deleteAll() = db.getDictionaryDao().deleteAllWords()

    fun getSavedWords() = db.getDictionaryDao().getAllWords()

    suspend fun getAllWords() = db.getDictionaryDao().getAllWordsList()

    suspend fun getOrderedByLikes() = db.getDictionaryDao().getAllWordsSortedByLikes()

    suspend fun getOrderedByDisLikes() = db.getDictionaryDao().getAllWordsSortedByDisLikes()

    suspend fun deleteWord(word: Word) = db.getDictionaryDao().deleteWord(word)
}