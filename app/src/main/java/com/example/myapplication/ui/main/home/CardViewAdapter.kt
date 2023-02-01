package com.example.myapplication.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardCellBinding
import com.example.myapplication.ui.main.home.CardViewHolder
import com.example.myapplication.ui.main.home.Clothes


class CardViewAdapter(
//<<<<<<< HEAD
//    private val clothes: List<Clothes>
//    // private val clickListener: HomeFragment
//) : RecyclerView.Adapter<CardViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
//
//        val from = LayoutInflater.from(parent.context)
//        val binding = CardCellBinding.inflate(from, parent, false)
//        return CardViewHolder(binding)
//=======
    private val clothes:List<Clothes>,
    private val clickListener: ClothesClickListener
)
    : RecyclerView.Adapter<CardViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val from= LayoutInflater.from(parent.context)
        val binding= CardCellBinding.inflate(from,parent,false)
        return CardViewHolder(binding,clickListener)
//>>>>>>> main
    }

    override fun getItemCount(): Int = clothes.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindClothes(clothes[position])
    }


}