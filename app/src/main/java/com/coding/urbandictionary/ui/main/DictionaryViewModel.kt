package com.coding.urbandictionary.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.widget.ProgressBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.coding.urbandictionary.model.DictionaryList
import com.coding.urbandictionary.model.Word
import com.coding.urbandictionary.repository.DictionaryRepository
import com.coding.urbandictionary.util.MyApplication
import com.coding.urbandictionary.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DictionaryViewModel(
    app: Application,
    val dictionaryRepository: DictionaryRepository
) : AndroidViewModel(app) {

    val word: MutableLiveData<Resource<DictionaryList>> = MutableLiveData()
    val list: MutableLiveData<List<Word>> = MutableLiveData()

    lateinit var progressBar: ProgressBar

    init {
        deleteAllWords()
        getWord("nike")
    }

    fun getWord(searchWord: String) = viewModelScope.launch {
        safeDictionaryCall(searchWord)
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

    // an option to save the last 5 words searched
    fun getSavedWords() = dictionaryRepository.getSavedWords()

    // delete the first word saved to replace with the latest
    fun deleteWord(word: Word) = viewModelScope.launch {
        dictionaryRepository.deleteWord(word)
    }

    private suspend fun safeDictionaryCall (searchTerm: String) {
        word.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = dictionaryRepository.getWord(searchTerm)
                word.postValue(handleDictionaryResponse(response))
            } else {
                word.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> word.postValue(Resource.Error("Network Failure"))
            }
        }
    }

    // check Network Connection
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MyApplication>().getSystemService (
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}