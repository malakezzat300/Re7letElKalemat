package com.malakezzat.re7letelkalemat.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malakezzat.re7letelkalemat.Model.Word
import com.malakezzat.re7letelkalemat.databinding.ItemPersonalCardBinding

class MyCardAdapter(val onItemClick: (Word) -> Unit) : ListAdapter<Word, MyCardAdapter.MyCardViewHolder>(MyDiffCallback()) {

    class MyDiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }

    class MyCardViewHolder(private val binding: ItemPersonalCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wordEntity: Word, onItemClick: (Word) -> Unit) {
            binding.cardTitle.text = wordEntity.word
            binding.root.setOnClickListener {
                onItemClick(wordEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCardViewHolder {
        val binding = ItemPersonalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCardViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}
