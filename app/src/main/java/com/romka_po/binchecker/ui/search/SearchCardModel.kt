package com.romka_po.binchecker.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romka_po.binchecker.model.Card
import com.romka_po.binchecker.model.CardMainInfo
import com.romka_po.binchecker.model.Resource
import com.romka_po.binchecker.repositories.CardRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Calendar
import java.util.Date

class SearchCardModel(
    private val cardRepository: CardRepository
):ViewModel() {

    val searchCard:MutableLiveData<Resource<Card>> = MutableLiveData()
    val date = getCurrentDateTime()

    fun getInfoCard(bin: String) = viewModelScope.launch {
        searchCard.postValue(Resource.Loading())
        val responce = cardRepository.getInfoCard(bin)
        searchCard.postValue(handleSearchCardInfoResponse(responce, bin ))

    }

    private fun handleSearchCardInfoResponse(responce: Response<Card>, bin: String):Resource<Card>{
        if (responce.isSuccessful){
            responce.body()?.let { resultResponce ->
                saveCard(CardMainInfo(bin, resultResponce.bank?.name, date ))
                return Resource.Success(resultResponce)
            }
        }
        return Resource.Error(responce.message())
    }

    fun saveCard(card: CardMainInfo) = viewModelScope.launch {
        cardRepository.insert(card)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


}