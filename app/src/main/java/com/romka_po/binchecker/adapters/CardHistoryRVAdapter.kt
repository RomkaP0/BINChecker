package com.romka_po.binchecker.adapters

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.romka_po.binchecker.R
import com.romka_po.binchecker.databinding.CardrvBinding
import com.romka_po.binchecker.model.CardMainInfo


class CardHistoryRVAdapter : RecyclerView.Adapter<CardHistoryRVAdapter.CardViewHolder>(
) {
    inner class CardViewHolder(val binding: CardrvBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<CardMainInfo>() {
        override fun areItemsTheSame(oldItem: CardMainInfo, newItem: CardMainInfo): Boolean {
            return oldItem.bin == newItem.bin
        }

        override fun areContentsTheSame(oldItem: CardMainInfo, newItem: CardMainInfo): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            CardrvBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = differ.currentList[position]
        holder.binding.apply {
            cardRVNum.text = card.bin.toString()
            cardRVBank.text = card.bank

            setOnClickListener {
                onItemClickListener?.let {
                    it(card)
                }
            }
        }
    }

    private var onItemClickListener: ((CardMainInfo) -> Unit)? = null

    fun setOnClickListener(listener: (CardMainInfo) -> Unit) {
        onItemClickListener = listener
    }
}