package com.romka_po.binchecker.ui.search

import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.romka_po.binchecker.CardDB
import com.romka_po.binchecker.converters.TransformFilterConverter
import com.romka_po.binchecker.databinding.FragmentSearchCardBinding
import com.romka_po.binchecker.model.Card
import com.romka_po.binchecker.model.Resource
import com.romka_po.binchecker.repositories.CardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SearchCardFragment : Fragment(), CoroutineScope by MainScope() {

    private var _binding: FragmentSearchCardBinding? = null
    lateinit var viewModel: SearchCardModel
    val TAG = "SearchCardFragment"

    val args: SearchCardFragmentArgs by navArgs()
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
    private lateinit var binInput:TextInputEditText
    private lateinit var cardBank:CardView
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repository = CardRepository(CardDB(requireActivity().applicationContext))
        val viewModelProviderFactory = SearchCardViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(SearchCardModel::class.java)

        _binding = FragmentSearchCardBinding.inflate(inflater, container, false)
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
                    hideProgressBar()
                    val data = response.data
                    checkAll(data!!)
                }
                is Resource.Error-> {
                    hideProgressBar()
                    response.message?.let { message->
                        Log.e(TAG, message)
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })

        if (args.bin!="-1"){
            binInput.setText(args.bin)
        }
    }



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
        latLabel.text = (data.country?.latitude.toString()+","+data.country?.longitude.toString())
        catchMap(latLabel)
    }

    fun catchMap(textView: TextView){
        val pattern = Pattern.compile("^(-?\\d+(\\.\\d+)?),\\s*(-?\\d+(\\.\\d+)?)\$")
        Linkify.addLinks(textView,pattern, "https://www.google.com/maps/search/?api=1&query=", null, TransformFilterConverter())
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
        binInput=binding.binInput
        cardBank=binding.cardBank
        progressBar=binding.searchprogressbar
    }

    fun showProgressBar(){
        progressBar.visibility=View.VISIBLE
    }
    fun hideProgressBar(){
        progressBar.visibility=View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}