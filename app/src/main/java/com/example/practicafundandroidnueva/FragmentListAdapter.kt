package com.example.practicafundandroidnueva

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafundandroidnueva.databinding.HeroeCellBinding
import com.squareup.picasso.Picasso

interface onClickGridItem{
    fun onClick(heroe: Heroe)
}

class FragmentListAdapter(
    private val heroesList: List<Heroe>,
    private val callback : onClickGridItem
): RecyclerView.Adapter<FragmentListAdapter.HeroesListFragmentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeroesListFragmentViewHolder {
        val binding = HeroeCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroesListFragmentViewHolder(binding, callback)
    }

    override fun onBindViewHolder(
        holder: HeroesListFragmentViewHolder,
        position: Int
    ) {
        holder.showHeroes(heroesList[position])
    }

    override fun getItemCount(): Int {
        return heroesList.size
    }


    class HeroesListFragmentViewHolder(private var item: HeroeCellBinding, private val callback: onClickGridItem): RecyclerView.ViewHolder(item.root){
        fun showHeroes(heroe: Heroe){
            item.tvName.text = heroe.name
            Picasso.get().load(heroe.photo).into(item.imageHeroeList)
            item.progressBarListHeroes.max = heroe.totalHitPoints
            item.progressBarListHeroes.progress = heroe.currentHitPoints
            item.heroeCell.setOnClickListener {

                callback.onClick(heroe)
            }

            if(heroe.isDead){
                item.imageHeroeList.foreground = ContextCompat.getDrawable(item.imageHeroeList.context, R.color.black)
                item.imageHeroeList.alpha = 0.6F
                item.heroeCell.isClickable = false
            }else{
                item.imageHeroeList.foreground = ContextCompat.getDrawable(item.imageHeroeList.context, R.color.blue)
                item.imageHeroeList.alpha = 1.0F
                item.heroeCell.isClickable = true
            }


        }
    }
}