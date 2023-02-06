package com.example.myapplication.ui.main.home.newclothe

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.ItemClickInterface
import com.example.myapplication.ui.main.home.Clothes
import com.example.myapplication.ui.main.home.clothesList
import com.example.myapplication.ui.main.home.newclothesList
import com.example.myapplication.ui.main.home.recent.CardViewAdapter
import com.example.myapplication.ui.store.storedetail.StoreActivity


class NewFragment : BaseFragment<FragmentNewBinding>(R.layout.fragment_new) ,
    ItemClickInterface {

    lateinit var fragmentadapter : CardViewAdapter
    override fun init() {
        rcView()
    }

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
        addClothes()
        fragmentadapter = CardViewAdapter(this@NewFragment)
        binding.newRecyclerView.apply {
            layoutManager= GridLayoutManager(this.context,2)
            adapter=fragmentadapter
            fragmentadapter.submitList(newclothesList.toMutableList())

        }
    }

    override fun onItemImageClick(view: View, position: Int) {
        //     val intent = Intent(getActivity(), ClothActivity::class.java)
        //    startActivity(intent)
    }

    override fun onItemMarketNameClick(view: View, position: Int) {
        val intent = Intent(getActivity(), StoreActivity::class.java)
        startActivity(intent)
    }

    override fun onItemButtonClick(view: View, position: Int) {

    }

    override fun onItemFavoriteClick(view: View, position: Int) {
        if (newclothesList[position].like == false) {
            //화면에 보여주기
            Glide.with(this@NewFragment)
                .load(R.drawable.icon_favorite_filledpink) //이미지
                .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
            newclothesList[position].like = true
        } else {
            //화면에 보여주기
            Glide.with(this@NewFragment)
                .load(R.drawable.icon_favorite_whiteline) //이미지
                .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
            newclothesList[position].like = false
        }
    }
}