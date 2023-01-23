package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardCellBinding

class CardViewAdapter(
    private val clothes:List<Clothes>,
    private val clickListener: ClothesClickListener)
    : RecyclerView.Adapter<CardViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val from=LayoutInflater.from(parent.context)
        val binding=CardCellBinding.inflate(from,parent,false)
        return CardViewHolder(binding,clickListener)
    }

    override fun getItemCount(): Int=clothes.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindClothes(clothes[position])
    }


}