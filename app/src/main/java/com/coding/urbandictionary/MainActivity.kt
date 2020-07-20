package com.coding.urbandictionary

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.media.session.MediaButtonReceiver.handleIntent
import com.coding.urbandictionary.db.DictionaryDatabase
import com.coding.urbandictionary.repository.DictionaryRepository
import com.coding.urbandictionary.ui.main.DictionaryFragment
import com.coding.urbandictionary.ui.main.DictionaryViewModel
import com.coding.urbandictionary.ui.main.DictionaryViewModelFactory
import com.coding.urbandictionary.util.CompanionClass

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
        handleIntent(intent)
    }

    @SuppressLint("MissingSuperCall")
    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }
    }

    fun doMySearch(value: String) {
        viewModel.getWord(value)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater?.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }
}