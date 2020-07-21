package com.coding.urbandictionary.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.urbandictionary.R
import com.coding.urbandictionary.adapters.DictionaryAdapter
import com.coding.urbandictionary.util.CompanionClass
import com.coding.urbandictionary.util.Resource
import kotlinx.android.synthetic.main.main_fragment.*

class DictionaryFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = DictionaryFragment()
    }

    lateinit var viewModel: DictionaryViewModel
    lateinit var dictionaryAdapter: DictionaryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()

        viewModel.progressBar = progressBar

        viewModel.word.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { wordResponse ->
                        dictionaryAdapter.differ.submitList(wordResponse.list)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(CompanionClass.TAG, "An error occured: $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        tv_sort_likes.setOnClickListener {
            viewModel.getAllWordsSortedByLikes()
            viewModel.list.observe(viewLifecycleOwner, Observer { response ->
                recyclerview_dict.layoutManager?.smoothScrollToPosition(recyclerview_dict, null, 0)
                response?.let { dictionaryAdapter.differ.submitList(it) }
            })
        }

        tv_sort_dislikes.setOnClickListener {
            viewModel.getAllWordsSortedByDisLikes()
            viewModel.list.observe(viewLifecycleOwner, Observer { response ->
                recyclerview_dict.layoutManager?.smoothScrollToPosition(recyclerview_dict, null, 0)
                response?.let { dictionaryAdapter.differ.submitList(it) }
            })
        }

        tv_sort_clear.setOnClickListener {
            viewModel.getAllWords()
            viewModel.list.observe(viewLifecycleOwner, Observer { response ->
                recyclerview_dict.layoutManager?.smoothScrollToPosition(recyclerview_dict, null, 0)
                response?.let { dictionaryAdapter.differ.submitList(it) }
            })
        }
    }

    private fun hideProgressBar() {
        viewModel.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        viewModel.progressBar.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        dictionaryAdapter = DictionaryAdapter()
        recyclerview_dict.apply {
            adapter = dictionaryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}