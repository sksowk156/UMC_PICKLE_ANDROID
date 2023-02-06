package com.example.myapplication.ui.main.favorite.item

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteItemBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.ItemClickInterface
import com.example.myapplication.ui.main.home.*
import com.example.myapplication.ui.main.home.recent.CardViewAdapter
import com.example.myapplication.ui.main.location.around.AroundAdapter
import com.example.myapplication.ui.store.ClothActivity
import com.example.myapplication.ui.store.StoreActivity
import kotlinx.android.synthetic.main.item_around_recycler.*


class FavoriteItemFragment :
    BaseFragment<FragmentFavoriteItemBinding>(R.layout.fragment_favorite_item), ItemClickInterface {

    lateinit var fragmentadapter: CardViewAdapter
    override fun init() {
        rcView()
    }

    private fun addClothes() {
        val clothes1 = Clothes(
            R.drawable.one,
            "store1",
            "옷1",
            30000,
            false
        )
        clothesList.add(clothes1)
        newclothesList.add(clothes1)

        val clothes2 = Clothes(
            R.drawable.two,
            "store2",
            "옷2",
            30000,
            false
        )
        clothesList.add(clothes2)
        newclothesList.add(clothes1)

        val clothes3 = Clothes(
            R.drawable.two,
            "store1",
            "옷3",
            30000,
            false
        )
        clothesList.add(clothes3)
        newclothesList.add(clothes1)

        val clothes4 = Clothes(
            R.drawable.one,
            "store1",
            "옷4",
            30000,
            false
        )
        clothesList.add(clothes4)
        newclothesList.add(clothes1)

        val clothes5 = Clothes(
            R.drawable.two,
            "store1",
            "옷5",
            30000,
            false
        )
        clothesList.add(clothes5)
        newclothesList.add(clothes1)

    }

    private fun rcView() {
        addClothes()
        fragmentadapter = CardViewAdapter(this)
        binding.favoriteItemRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = fragmentadapter
            fragmentadapter.submitList(newclothesList.toMutableList())
        }
    }

    override fun onItemImageClick(view: View, position: Int) {
        val intent = Intent(context, ClothActivity::class.java)
        intent.putExtra("storeName", "store1")
        intent.putExtra("clothName", "옷1")
        intent.putExtra("clothPrice", 30000)

        startActivity(intent)
    }

    override fun onItemMarketNameClick(view: View, position: Int) {
        val intent = Intent(context, StoreActivity::class.java)
        startActivity(intent)
    }

    override fun onItemButtonClick(view: View, position: Int) {
        showToast("clicked!!")
    }

    override fun onItemFavoriteClick(view: View, position: Int) {
        if (newclothesList[position].like == false) {
            //화면에 보여주기
            Glide.with(this@FavoriteItemFragment)
                .load(R.drawable.icon_favorite_filledpink) //이미지
                .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
            newclothesList[position].like = true
        } else {
            //화면에 보여주기
            Glide.with(this@FavoriteItemFragment)
                .load(R.drawable.icon_favorite_whiteline) //이미지
                .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
            newclothesList[position].like = false
        }
    }
}