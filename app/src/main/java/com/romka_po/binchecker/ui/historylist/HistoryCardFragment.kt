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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.romka_po.binchecker.CardDB
import com.romka_po.binchecker.R
import com.romka_po.binchecker.adapters.CardHistoryRVAdapter
import com.romka_po.binchecker.databinding.FragmentHistoryListBinding
import com.romka_po.binchecker.repositories.CardRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HistoryCardFragment : Fragment() {

    private var _binding: FragmentHistoryListBinding? = null
    private lateinit var viewModel: HistoryCardModel
    private lateinit var cardAdapter:CardHistoryRVAdapter
    lateinit var fabClear:FloatingActionButton
    lateinit var fabTop:FloatingActionButton

    private val binding get() = _binding!!
    private lateinit var recyclerView:RecyclerView

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
        fabTop = binding.fabtop
        fabClear = binding.fabclear
        setupRecyclerView()
        var job: Job? = null
        fabClear.setOnClickListener {
            job?.cancel()
            job = MainScope().launch {
                viewModel.clear()
            }
            fabClear.hide()
        }

        cardAdapter.setOnItemClickListener{
            val bundle = Bundle().apply {
                putString("bin", it.bin)
            }
            findNavController().navigate(R.id.navigation_search_card,bundle)
        }

        viewModel.getSavedCard().observe(viewLifecycleOwner, Observer { cards ->
            cardAdapter.differ.submitList(cards)
            if (cardAdapter.differ.currentList.isEmpty()){
                fabClear.hide()
                fabTop.hide()
            }
            else
                fabClear.show()

        })
        fabTop.setOnClickListener {
            scrollToTop()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupRecyclerView(){
        cardAdapter = CardHistoryRVAdapter()
        recyclerView.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(object  : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy>0){
                        fabTop.show()
                    }
                    else
                        fabTop.hide()
                }
            })

        }
    }

    fun scrollToTop(){
        val layout = recyclerView.layoutManager as LinearLayoutManager
        layout.scrollToPositionWithOffset(0,0)
    }

}