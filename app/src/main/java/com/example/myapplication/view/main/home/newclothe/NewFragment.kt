package com.example.myapplication.view.main.home.newclothe

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewBinding
import com.example.myapplication.data.remote.model.DressOverviewDto
import com.example.myapplication.data.remote.model.UpdateDressLikeDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.widget.utils.ItemCardClickInterface
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.view.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.view.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.widget.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewFragment : BaseFragment<FragmentNewBinding>(R.layout.fragment_new),
    ItemCardClickInterface {
    val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()

    private lateinit var fragmentadapter: HomeRecommendAdapter

    private var newData = ArrayList<DressOverviewDto>()
    private var buttonClick = false

    override fun init() {
        initAppbar(binding.newToolbar, "NEW", true, false)
        initRecyclerView()

//        dressViewModel.dress_like_data.observe(viewLifecycleOwner, Observer<List<DressLikeDto>> {
////            if (update_list_position != null) {
////                newData[update_list_position!!].dress_like =
////                    !newData[update_list_position!!].dress_like!!
//////                    newAdapter.submitList(newData.toMutableList())
////                fragmentadapter.notifyItemChanged(update_list_position!!)
////
////                update_list_position = null
////            }
//        })
    }

    private fun initRecyclerView() {
        fragmentadapter = HomeRecommendAdapter(this@NewFragment)

        dressViewModel.dress_like_data.observe(this@NewFragment, Observer {
            when(it){
                is NetworkResult.Loading ->{

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Success ->{
                    homeViewModel.get_home_new_data(homeViewModel.home_latlng.value!!.first, homeViewModel.home_latlng.value!!.second)
                }
            }
        })

        homeViewModel.home_new_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {
                    Log.d("whatisthis", "NewFragment : " + it.message.toString())
                    fragmentadapter.submitList(null)
                }
                is NetworkResult.Success -> {
                    newData = it.data!! as ArrayList<DressOverviewDto>
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

    override fun onResume() {
        super.onResume()
        if(buttonClick){
            homeViewModel.get_home_new_data(homeViewModel.home_latlng.value!!.first, homeViewModel.home_latlng.value!!.second)
            buttonClick = false
        }
    }
}