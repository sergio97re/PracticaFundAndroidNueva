package com.example.practicafundandroidnueva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.practicafundandroidnueva.databinding.FragmentDetalleBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class DetalleFragment : Fragment() {

    private lateinit var binding: FragmentDetalleBinding
    val coreViewModel: CoreViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetalleBinding.inflate(inflater)


        viewLifecycleOwner.lifecycleScope.launch {
            coreViewModel.uiState.collect{
                binding.textViewNameHeroeFight.text = coreViewModel.selectedHeroe.name
                Picasso.get().load(coreViewModel.selectedHeroe.photo).into(binding.imageHeroeFight)
                binding.progressBarFight.max = coreViewModel.selectedHeroe.totalHitPoints
                binding.progressBarFight.progress = coreViewModel.selectedHeroe.currentHitPoints
                binding.numberProgress.text = "${coreViewModel.selectedHeroe.currentHitPoints}%"


            }
        }
        setFightButtonsOnClickMethods()
        return binding.root
    }

    private fun setFightButtonsOnClickMethods(){
        binding.bnHeal.setOnClickListener {
            coreViewModel.fightOnClickMethod(binding.bnHeal.tag.toString())
        }
        binding.bnAtack.setOnClickListener {
            coreViewModel.fightOnClickMethod(binding.bnAtack.tag.toString())
        }
    }
}