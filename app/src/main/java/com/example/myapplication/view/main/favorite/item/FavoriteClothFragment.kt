package com.example.myapplication.view.main.favorite.item

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFavoriteItemBinding
import com.example.myapplication.data.remote.model.UpdateDressLikeDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.widget.utils.ItemCardClickInterface
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.view.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.widget.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteClothFragment :
    BaseFragment<FragmentFavoriteItemBinding>(R.layout.fragment_favorite_item),
    ItemCardClickInterface {

    val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()

    lateinit var fragmentadapter: FavoriteItemAdapter

    override fun init() {
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        dressViewModel.get_dress_like_data()
    }

    private fun initRecyclerView() {
        fragmentadapter = FavoriteItemAdapter(this)

        dressViewModel.dress_like_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Log.d("whatisthis","FavoriteClothFragment : 데이터없음")
                }

                is NetworkResult.Success -> {
                    fragmentadapter.submitList(it.data!!.toMutableList())
                }
            }
        })

        binding.favoriteItemRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = fragmentadapter
        }
    }

    override fun onItemClothImageClick(id: Int, position: Int) {
        val intent = Intent(context, ClothActivity::class.java)
        intent.putExtra("cloth_id", id)
        startActivity(intent)
    }

    override fun onItemStoreNameClick(id: Int, position: Int) {
        val intent = Intent(context, StoreActivity::class.java)
        intent.putExtra("store_id", id)
        startActivity(intent)
    }


    override fun onItemClothFavoriteClick(like:Boolean, id: Int, view : View, position: Int) {
        dressViewModel.set_dress_like_data(UpdateDressLikeDto(id))
        dressViewModel.get_dress_like_data()
    }

}