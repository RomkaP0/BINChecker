package com.romka_po.binchecker.repositories

import com.romka_po.binchecker.CardDB
import com.romka_po.binchecker.adapters.ApiAdapter

class CardRepository(
    val db:CardDB){

    suspend fun getInfoCard(string: String)=
      ApiAdapter.apiClient.getInfoCard(string)

}

//launch(Dispatchers.Main) {
//            try {
//                val response = ApiAdapter.apiClient.getInfoCard(string)
//                if (response.isSuccessful && response.body() != null) {
//                    val data = response.body()!!
//                    checkAll(data)
//                } else {
//                    Toast.makeText(
//                        context,
//                        "Server is not available",
//                        Toast.LENGTH_LONG).show()
//                    Log.d("else", response.message())
//                }
//            } catch (e: Exception) {
//                Toast.makeText(context,
//                    "Internet doesn`t work",
//                    Toast.LENGTH_LONG).show()
//                e.message?.let { Log.d("try", it) }
//            }
//        }