package com.romka_po.binchecker.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.romka_po.binchecker.CardDB
import com.romka_po.binchecker.MainActivity
import com.romka_po.binchecker.adapters.ApiAdapter
import com.romka_po.binchecker.databinding.FragmentDashboardBinding
import com.romka_po.binchecker.model.Card
import com.romka_po.binchecker.model.Resource
import com.romka_po.binchecker.repositories.CardRepository
import com.romka_po.binchecker.ui.CardViewModel
import com.romka_po.binchecker.ui.CardViewModelProviderFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.Console

class DashboardFragment : Fragment(), CoroutineScope by MainScope() {

    private var _binding: FragmentDashboardBinding? = null
    lateinit var viewModel: CardViewModel
    val TAG = "DashboardFragment"
    private val binding get() = _binding!!
    private lateinit var bankLabel:TextView
    private lateinit var phoneLabel:TextView
    private lateinit var urlLabel:TextView
    private lateinit var companyLabel:TextView
    private lateinit var brandLabel:TextView
    private lateinit var typeLabel:TextView
    private lateinit var prepaidLabel:TextView
    private lateinit var lenLabel:TextView
    private lateinit var luhnLabel:TextView
    private lateinit var countryLabel:TextView
    private lateinit var currencyLabel:TextView
    private lateinit var latLabel:TextView
    private lateinit var longLabel:TextView
    private lateinit var binInput:TextInputEditText
    private lateinit var cardBank:CardView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repository = CardRepository(CardDB(requireActivity().applicationContext))
        val viewModelProviderFactory = CardViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(CardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectXML()
        var job: Job? = null
        binInput.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                editable?.let {
                    if (editable.toString().length==8){
                        viewModel.getInfoCard(editable.toString())
                    }
                }
            }
        }

        viewModel.searchCard.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.Success ->{
                    //hideProgressBar()
                    val data = response.data
                    checkAll(data!!)
                }
                is Resource.Error-> {
                    //hideProgressBar()
                    response.message?.let { message->
                        Log.e(TAG, message)
                    }
                }
                is Resource.Loading ->{
                    //showProgressBar
                }
            }
        })
    }

//    fun getInfo(string: String, viewGroup: ViewGroup){
//        launch(Dispatchers.Main) {
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
//    }

    fun checkAll(data:Card){
        bankLabel.text = data.bank?.name
        phoneLabel.text = data.bank?.phone
        urlLabel.text = data.bank?.url
        companyLabel.text = data.scheme
        brandLabel.text = data.brand
        typeLabel.text = data.type
        prepaidLabel.text = data.prepaid.toString()
        lenLabel.text = data.number?.length.toString()
        luhnLabel.text = data.number?.luhn.toString()
        countryLabel.text = data.country?.name
        currencyLabel.text = data.country?.currency
        latLabel.text = data.country?.latitude.toString()
        longLabel.text = data.country?.longitude.toString()
    }


    fun connectXML(){
        bankLabel=binding.bankName
        phoneLabel=binding.phone
        urlLabel=binding.url
        companyLabel=binding.company
        brandLabel=binding.brand
        typeLabel=binding.type
        prepaidLabel=binding.prepaid
        lenLabel=binding.length
        luhnLabel=binding.luhn
        countryLabel=binding.country
        currencyLabel=binding.currency
        latLabel=binding.lat
        longLabel=binding.longitude
        binInput=binding.binInput
        cardBank=binding.cardBank
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}