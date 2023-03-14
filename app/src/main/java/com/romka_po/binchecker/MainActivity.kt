package com.romka_po.binchecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.romka_po.binchecker.databinding.ActivityMainBinding
import com.romka_po.binchecker.repositories.CardRepository
import com.romka_po.binchecker.ui.CardViewModel
import com.romka_po.binchecker.ui.CardViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val repository = CardRepository(CardDB(this))
//        val viewModelProviderFactory = CardViewModelProviderFactory(repository)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(CardViewModel::class.java)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)
    }
}