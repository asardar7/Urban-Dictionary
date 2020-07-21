package com.coding.urbandictionary.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.coding.urbandictionary.model.Word

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord (word: Word)

    @Insert
    fun insertWordTest (word: Word)

    @Query("DELETE FROM dictionary_table")
    suspend fun deleteAllWords()

    @Delete
    suspend fun deleteWord (word: Word)

    @Query("SELECT * FROM dictionary_table")
    fun getAllWords() : LiveData<List<Word>>

    @Query("SELECT * FROM dictionary_table")
    suspend fun getAllWordsList() : List<Word>

    @Query("SELECT * FROM dictionary_table")
    fun getAllWordsListTest() : List<Word>

    @Query("SELECT * FROM dictionary_table ORDER BY thumbs_up DESC")
    suspend fun getAllWordsSortedByLikes() : List<Word>

    @Query("SELECT * FROM dictionary_table ORDER BY thumbs_down DESC")
    suspend fun getAllWordsSortedByDisLikes() : List<Word>
}