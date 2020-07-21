package com.coding.urbandictionary.ui.main

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.urbandictionary.model.DictionaryList
import com.coding.urbandictionary.model.Word
import com.coding.urbandictionary.repository.DictionaryRepository
import com.coding.urbandictionary.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class DictionaryViewModel(
    val dictionaryRepository: DictionaryRepository
) : ViewModel() {

    val word: MutableLiveData<Resource<DictionaryList>> = MutableLiveData()
    val list: MutableLiveData<List<Word>> = MutableLiveData()

    lateinit var progressBar: ProgressBar

    init {
        deleteAllWords()
        getWord("nike")
    }

    fun getWord(searchWord: String) = viewModelScope.launch {
        word.postValue(Resource.Loading())

        val response = dictionaryRepository.getWord(searchWord)
        word.postValue(handleDictionaryResponse(response))
    }

    private fun handleDictionaryResponse(response: Response<DictionaryList>): Resource<DictionaryList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                saveWord(resultResponse.list)
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }

    fun saveWord(word: List<Word>) = viewModelScope.launch {
        for (w in word) {
            dictionaryRepository.insert(w)
        }
    }

    fun deleteAllWords() = viewModelScope.launch {
        dictionaryRepository.deleteAll()
    }

    fun getAllWordsSortedByLikes() = viewModelScope.launch {
        list.postValue(dictionaryRepository.getOrderedByLikes())
    }

    fun getAllWordsSortedByDisLikes() = viewModelScope.launch {
        list.postValue(dictionaryRepository.getOrderedByDisLikes())
    }

    fun getAllWords() = viewModelScope.launch {
        list.postValue(dictionaryRepository.getAllWords())
    }

    fun getSavedWords() = dictionaryRepository.getSavedWords()

    fun deleteWord(word: Word) = viewModelScope.launch {
        dictionaryRepository.deleteWord(word)
    }
}