package com.romka_po.binchecker.ui.historylist

import androidx.lifecycle.ViewModel
import com.romka_po.binchecker.repositories.CardRepository

class HistoryCardModel(
    private val cardRepository: CardRepository
): ViewModel() {

    fun getSavedCard() = cardRepository.getAllCard()

    suspend fun clear() = cardRepository.clear()
}