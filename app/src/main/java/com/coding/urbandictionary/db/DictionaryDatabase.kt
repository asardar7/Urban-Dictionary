package com.coding.urbandictionary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coding.urbandictionary.model.Word

@Database (
    entities = [Word::class],
    version = 1
)
abstract class DictionaryDatabase: RoomDatabase() {
    abstract fun getDictionaryDao(): DictionaryDao

    companion object {
        @Volatile
        private var instance: DictionaryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DictionaryDatabase::class.java,
                "dictionary_db.db"
            ).build()
    }
}