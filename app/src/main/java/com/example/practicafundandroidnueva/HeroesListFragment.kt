package com.example.practicafundandroidnueva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practicafundandroidnueva.databinding.FragmentHeroesListBinding
import kotlinx.coroutines.launch

class HeroesListFragment : Fragment(),onClickGridItem {

    private lateinit var binding: FragmentHeroesListBinding
    val coreViewModel: CoreViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroesListBinding.inflate(inflater)
        setFloatingHealAllHeroesButton()

        val adapter = FragmentListAdapter(coreViewModel.heroesList,this)
        binding.rvHeroesList.layoutManager = GridLayoutManager(binding.rvHeroesList.context,2)
        binding.rvHeroesList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch{
            coreViewModel.uiState.collect{
                adapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

    override fun onClick(heroe: Heroe) {
        coreViewModel.selectedHeroToFightClicked(heroe)
    }

    private fun setFloatingHealAllHeroesButton(){
        binding.buttonHealtHeroe.setOnClickListener {
            coreViewModel.healAllHeroes()
            Toast.makeText(binding.buttonHealtHeroe.context,"Todos los heroes han sido curados", Toast.LENGTH_LONG).show()


        }
    }



}