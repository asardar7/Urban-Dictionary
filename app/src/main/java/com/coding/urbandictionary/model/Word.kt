package com.coding.urbandictionary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_table")
data class Word (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var definition: String,
    var word: String,
    var thumbs_up: Int,
    var thumbs_down: Int)