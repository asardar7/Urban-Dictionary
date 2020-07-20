package com.coding.urbandictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.coding.urbandictionary.db.DictionaryDatabase
import com.coding.urbandictionary.repository.DictionaryRepository
import com.coding.urbandictionary.ui.main.DictionaryFragment
import com.coding.urbandictionary.ui.main.DictionaryViewModel
import com.coding.urbandictionary.ui.main.DictionaryViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: DictionaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val repository = DictionaryRepository(DictionaryDatabase(this))
        val viewModelProviderFactory = DictionaryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(DictionaryViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DictionaryFragment.newInstance())
                .commitNow()
        }
    }
}