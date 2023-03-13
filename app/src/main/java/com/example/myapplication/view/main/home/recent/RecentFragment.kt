package com.example.myapplication.view.main.home.recent

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRecentBinding
import com.example.myapplication.data.remote.model.DressOverviewDto
import com.example.myapplication.data.remote.model.UpdateDressLikeDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.view.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.widget.utils.ItemCardClickInterface
import com.example.myapplication.widget.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment : BaseFragment<FragmentRecentBinding>(R.layout.fragment_recent),
    ItemCardClickInterface {

    val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()

    lateinit var fragmentadapter: HomeRecommendAdapter

    private var recentData = ArrayList<DressOverviewDto>()
    private var buttonClick = false

    override fun init() {
        initAppbar(binding.recentToolbar, "최근 본 상품", true, false)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        fragmentadapter = HomeRecommendAdapter(this@RecentFragment)

        dressViewModel.dress_like_data.observe(this@RecentFragment, Observer {
            when(it){
                is NetworkResult.Loading ->{

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Success ->{
                    homeViewModel.get_home_recent_data()
                }
            }
        })

        homeViewModel.home_recent_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {
                    Log.d("whatisthis", "RecentFragment : " + it.message.toString())
                    fragmentadapter.submitList(null)
                }
                is NetworkResult.Success -> {
                    recentData = it.data!! as ArrayList<DressOverviewDto>
                    fragmentadapter.submitList(it.data!!)
                }
            }
        })

        binding.newRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = fragmentadapter
        }
    }

    override fun onItemClothImageClick(id: Int, position: Int) {
        buttonClick = true

        val intent = Intent(getActivity(), ClothActivity::class.java)
        intent.putExtra("cloth_id", id)
        startActivity(intent)
    }

    override fun onItemStoreNameClick(id: Int, position: Int) {
        buttonClick = true

        val intent = Intent(getActivity(), StoreActivity::class.java)
        intent.putExtra("store_id", id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if(buttonClick){
            homeViewModel.get_home_recent_data()
            buttonClick = false
        }
    }

    override fun onItemClothFavoriteClick(like: Boolean, id: Int, view: View, position: Int) {
        if (id != 0) {
            dressViewModel.set_dress_like_data(UpdateDressLikeDto(id))
            dressViewModel.get_dress_like_data()

            homeViewModel.get_home_data(
                homeViewModel.home_latlng.value!!.first,
                homeViewModel.home_latlng.value!!.second
            )
        }
    }

}