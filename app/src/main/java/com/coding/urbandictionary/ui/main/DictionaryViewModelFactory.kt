package com.coding.urbandictionary.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coding.urbandictionary.repository.DictionaryRepository

class DictionaryViewModelFactory (
    val app: Application,
    val dictionaryRepository: DictionaryRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DictionaryViewModel(app, dictionaryRepository) as T
    }
}