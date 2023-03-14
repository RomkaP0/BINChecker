package com.romka_po.binchecker.ui.historylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.binchecker.repositories.CardRepository

class HistoryCardViewModelProviderFactory(
    val cardsRepository: CardRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryCardModel(cardsRepository) as T
    }
}