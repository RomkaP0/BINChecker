package com.romka_po.binchecker.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.binchecker.repositories.CardRepository

class SearchCardViewModelProviderFactory(
    val cardsRepository:CardRepository
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchCardModel(cardsRepository) as T
    }
}