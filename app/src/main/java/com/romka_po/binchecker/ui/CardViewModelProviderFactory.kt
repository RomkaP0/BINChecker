package com.romka_po.binchecker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.binchecker.repositories.CardRepository

class CardViewModelProviderFactory(
    val cardsRepository:CardRepository
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CardViewModel(cardsRepository) as T
    }
}