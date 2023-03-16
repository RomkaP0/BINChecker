package com.romka_po.binchecker.ui.historylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romka_po.binchecker.CardDB
import com.romka_po.binchecker.R
import com.romka_po.binchecker.adapters.CardHistoryRVAdapter
import com.romka_po.binchecker.databinding.FragmentHistoryListBinding
import com.romka_po.binchecker.repositories.CardRepository

class HistoryCardFragment : Fragment() {

    private var _binding: FragmentHistoryListBinding? = null
    lateinit var viewModel: HistoryCardModel
    lateinit var cardAdapter:CardHistoryRVAdapter

    private val binding get() = _binding!!
    lateinit var recyclerView:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repository = CardRepository(CardDB(requireActivity().applicationContext))
        val viewModelProviderFactory = HistoryCardViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(HistoryCardModel::class.java)
        _binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        val root: View = binding.root




        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rv
        setupRecyclerView()

        cardAdapter.setOnItemClickListener{
            val bundle = Bundle().apply {
                putString("bin", it.bin)
            }
            findNavController().navigate(R.id.navigation_search_card,bundle
            )
        }

        viewModel.getSavedCard().observe(viewLifecycleOwner, Observer { cards ->
            cardAdapter.differ.submitList(cards)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun setupRecyclerView(){
        cardAdapter = CardHistoryRVAdapter()
        recyclerView.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }
}