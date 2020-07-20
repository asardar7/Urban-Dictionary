package com.coding.urbandictionary.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coding.urbandictionary.repository.DictionaryRepository

class DictionaryViewModelFactory (
    val dictionaryRepository: DictionaryRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DictionaryViewModel(dictionaryRepository) as T
    }
}