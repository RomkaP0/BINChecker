package com.romka_po.binchecker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.romka_po.binchecker.databinding.CardrvBinding
import com.romka_po.binchecker.model.CardMainInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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
            cardRVNum.text = card.bin
            cardRVBank.text = card.bank
            cardRVDate.text = card.date?.toString("dd/MM/yyyy")

        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(card) }
        }
    }

    private var onItemClickListener: ((CardMainInfo) -> Unit)? = null

    fun setOnItemClickListener(listener: (CardMainInfo) -> Unit) {
        onItemClickListener = listener
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }


}