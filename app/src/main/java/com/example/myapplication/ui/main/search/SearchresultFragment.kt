package com.example.myapplication.ui.main.search

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchresultBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.home.*
import com.example.myapplication.ui.store.ClothActivity
import com.example.myapplication.ui.store.StoreActivity

class SearchresultFragment : BaseFragment<FragmentSearchresultBinding>(R.layout.fragment_searchresult) {
    override fun init() {     rcView()
    }

    lateinit var fragmentadapter: CardViewAdapter

    private fun addClothes(){
        val clothes1= Clothes(
            R.drawable.one,
            "store1",
            "옷1",
            30000,
            false
        )
        clothesList.add(clothes1)
        newclothesList.add(clothes1)

        val clothes2= Clothes(
            R.drawable.two,
            "store2",
            "옷2",
            30000,
            false
        )
        clothesList.add(clothes2)
        newclothesList.add(clothes1)

        val clothes3= Clothes(
            R.drawable.two,
            "store1",
            "옷3",
            30000,
            false
        )
        clothesList.add(clothes3)
        newclothesList.add(clothes1)

        val clothes4= Clothes(
            R.drawable.one,
            "store1",
            "옷4",
            30000,
            false
        )
        clothesList.add(clothes4)
        newclothesList.add(clothes1)

        val clothes5= Clothes(
            R.drawable.two,
            "store1",
            "옷5",
            30000,
            false
        )
        clothesList.add(clothes5)
        newclothesList.add(clothes1)

    }

    private fun rcView(){
        clothesList.clear()
        addClothes()
        fragmentadapter = CardViewAdapter(clicklistener = (object : CardViewAdapter.ClothesClickListener{
            override fun onItemImageClick(view: View, position: Int) {
                val intent = Intent(context, ClothActivity::class.java)
                intent.putExtra("storeName","store1")
                intent.putExtra("clothName","옷1")
                intent.putExtra("clothPrice",30000)
                //intent.putExtra("StoreName",20000)

                startActivity(intent)
            }

            override fun onItemMarketNameClick(view: View, position: Int) {
                val intent = Intent(getActivity(), StoreActivity::class.java)
                startActivity(intent)
            }

            override fun onItemButtonClick(view: View, position: Int) {
                showToast("clicked")
            }
        }))
        fragmentadapter.submitList(clothesList.toMutableList())
        binding.searchresultRecycler.apply {
            layoutManager= GridLayoutManager(this.context,2)
            adapter= fragmentadapter
        }
        binding.searchresultTextviewResultcount.text = String.format("검색 결과 %d개", clothesList.size)
    }
}
