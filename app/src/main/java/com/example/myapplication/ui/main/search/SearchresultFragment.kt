package com.example.myapplication.ui.main.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchresultBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.home.*
import com.example.myapplication.ui.store.StoreActivity
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController.ClickListener

class SearchresultFragment : BaseFragment<FragmentSearchresultBinding>(R.layout.fragment_searchresult) {
    override fun init() {
        rcView()
    }

    private fun addClothes(){
        val clothes1= Clothes(
            R.drawable.one,
            "store1",
            "옷1",
            30000
        )
        clothesList.add(clothes1)
        newclothesList.add(clothes1)

        val clothes2= Clothes(
            R.drawable.two,
            "store2",
            "옷2",
            30000
        )
        clothesList.add(clothes2)
        newclothesList.add(clothes1)

        val clothes3= Clothes(
            R.drawable.two,
            "store1",
            "옷3",
            30000
        )
        clothesList.add(clothes3)
        newclothesList.add(clothes1)

        val clothes4= Clothes(
            R.drawable.one,
            "store1",
            "옷4",
            30000
        )
        clothesList.add(clothes4)
        newclothesList.add(clothes1)

        val clothes5= Clothes(
            R.drawable.two,
            "store1",
            "옷5",
            30000
        )
        clothesList.add(clothes5)
        newclothesList.add(clothes1)

    }

    private fun rcView(){
        clothesList.clear()
        addClothes()
        binding.searchresultRecycler.apply {
            layoutManager= GridLayoutManager(this.context,2)
            adapter= CardViewAdapter(clothesList, clickListener = (object : ClothesClickListener{
                override fun onClick(clothes: Clothes) {

                }

                override fun onClickStore(string: String) {
                    val intent = Intent(getActivity(), StoreActivity::class.java)
                    startActivity(intent)
                }
            }))
        }
        binding.searchresultTextviewResultcount.text = String.format("검색 결과 %d개", clothesList.size)
    }
}