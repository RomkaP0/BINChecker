package com.romka_po.binchecker.repositories

import com.romka_po.binchecker.CardDB
import com.romka_po.binchecker.adapters.ApiAdapter
import com.romka_po.binchecker.model.CardMainInfo

class CardRepository(
    val db:CardDB){

    suspend fun getInfoCard(string: String)=
      ApiAdapter.apiClient.getInfoCard(string)

    suspend fun insert(card:CardMainInfo) = db.getCardDBDao().insert(card)

    fun getAllCard() =db.getCardDBDao().getAllCard()

    suspend fun clear() = db.getCardDBDao().clear()
}
