package com.romka_po.binchecker.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romka_po.binchecker.model.Card
import com.romka_po.binchecker.model.Resource
import com.romka_po.binchecker.repositories.CardRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CardViewModel(
    val cardRepository: CardRepository
):ViewModel() {

    val searchCard:MutableLiveData<Resource<Card>> = MutableLiveData()

    fun getInfoCard(bin: String) = viewModelScope.launch {
        searchCard.postValue(Resource.Loading())
        val responce = cardRepository.getInfoCard(bin)
        searchCard.postValue(handleSearchCardInfoResponse(responce ))
    }
    private fun handleSearchCardInfoResponse(responce: Response<Card>):Resource<Card>{
        if (responce.isSuccessful){
            responce.body()?.let { resultResponce ->
                return Resource.Success(resultResponce)
            }
        }
        return Resource.Error(responce.message())
    }
}