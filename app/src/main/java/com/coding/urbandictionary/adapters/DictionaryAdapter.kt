package com.coding.urbandictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coding.urbandictionary.R
import com.coding.urbandictionary.model.Word
import kotlinx.android.synthetic.main.word_list.view.*

class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.WordViewHolder>() {

    inner class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object: DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.definition == newItem.definition
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DictionaryAdapter.WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.word_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = differ.currentList[position]
        holder.itemView.apply {
            tv_word.text = word.word
            tv_definition.text = word.definition
            tv_thumb_up.text = word.thumbs_up.toString()
            tv_thumb_down.text = word.thumbs_down.toString()
        }
    }
}