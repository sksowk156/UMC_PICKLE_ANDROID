package com.example.myapplication.view.main.home

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.data.remote.model.*
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.ItemCardClickInterface
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.view.main.home.newclothe.NewFragment
import com.example.myapplication.view.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.view.main.home.recent.RecentFragment
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.view.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.viewmodel.StoreViewModel
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.widget.config.EventObserver
import com.example.myapplication.widget.utils.NetworkResult
import com.smarteist.autoimageslider.SliderView

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    ItemCardClickInterface {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dressViewModel: DressViewModel
    private lateinit var storeViewModel: StoreViewModel
    private lateinit var userViewModel: UserViewModel

    private lateinit var recentAdapter: HomeRecentAdapter
    private lateinit var newAdapter: HomeNewAdapter
    private lateinit var recommendAdapter: HomeRecommendAdapter

    private lateinit var imageList: ArrayList<Int>

    override fun init() {
        homeViewModel = (activity as SecondActivity).homeViewModel
        dressViewModel = (activity as SecondActivity).dressViewModel
        storeViewModel = (activity as SecondActivity).storeViewModel
        userViewModel = (activity as SecondActivity).userViewModel

        binding.homevm = homeViewModel
        binding.uservm = userViewModel

        initAppbar(binding.homeToolbar, "홈", false, true)
        initSlideView()
        initRecyclerView()

        userViewModel.user_profile_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    userViewModel.set_home_user_name_data("피클")
                }

                is NetworkResult.Success -> {
                    userViewModel.set_home_user_name_data(it.data?.data!!.name)
                }
            }
        })

        homeViewModel.apply {
            recent_bt_event.observe(this@HomeFragment, EventObserver {
                parentFragmentManager
                    .beginTransaction()
                    .add(R.id.home_base_layout, RecentFragment(), "recent")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
                homeViewModel.get_home_recent_data()
            })
        }

        homeViewModel.apply {
            new_bt_event.observe(this@HomeFragment, EventObserver{
                parentFragmentManager
                    .beginTransaction()
                    .add(R.id.home_base_layout, NewFragment(), "new")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
                homeViewModel.get_home_new_data(
                    homeViewModel.home_latlng.value!!.first,
                    homeViewModel.home_latlng.value!!.second
                )
            })
        }

//        dressViewModel.dress_like_data.observe(viewLifecycleOwner, Observer<List<DressLikeDto>> {
////            recentAdapter.notifyDataSetChanged()
////
////            newAdapter.notifyDataSetChanged()
////
////            recommendAdapter.notifyDataSetChanged()
//
////            if (update_list_position != null) {
////
////                if ((recentData.size >= update_list_position!! +1) &&recentData[update_list_position!!].dress_id == update_islikedata_id) {
////                    recentData[update_list_position!!].dress_like = !recentData[update_list_position!!].dress_like!!
//////                    recentAdapter.submitList(recentData.toMutableList())
//////                    recentAdapter.updateData(recentData)
////                    recentAdapter.notifyItemChanged(update_list_position!!)
////                } else if ((newData.size >= update_list_position!! +1) && newData[update_list_position!!].dress_id == update_islikedata_id) {
////                    newData[update_list_position!!].dress_like = !newData[update_list_position!!].dress_like!!
//////                    newAdapter.submitList(newData.toMutableList())
////                    newAdapter.notifyItemChanged(update_list_position!!)
////                } else if((recommendData.size >= update_list_position!! +1) && recommendData[update_list_position!!].dress_id == update_islikedata_id){
////                    recommendData[update_list_position!!].dress_like = !recommendData[update_list_position!!].dress_like!!
//////                    recommendAdapter.submitList(recommendData.toMutableList())
////                    recommendAdapter.notifyItemChanged(update_list_position!!)
////                }
////                update_list_position = null
////            }
//        })
    }

    private fun initRecyclerView() {
        recentAdapter = HomeRecentAdapter(this@HomeFragment)
        newAdapter = HomeNewAdapter(this@HomeFragment)
        recommendAdapter = HomeRecommendAdapter(this@HomeFragment)

        homeViewModel.home_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Log.d("whatisthis", "HomeFragment : "+it.message.toString())
                    recentAdapter.submitList(null)
                    newAdapter.submitList(null)
                    recommendAdapter.submitList(null)
                }

                is NetworkResult.Success -> {
//                    recentAdapter.submitList(null)
//                    newAdapter.submitList(null)
//                    recommendAdapter.submitList(null)

                    recentAdapter.submitList(it.data!!.recentView!!.toMutableList())
                    newAdapter.submitList(it.data!!.newDresses!!.toMutableList())
                    recommendAdapter.submitList(it.data!!.recDresses!!.toMutableList())
                }
            }

        })

        binding.homeRecyclerviewRecent.apply {
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recentAdapter
        }

        binding.homeRecyclerviewNew.apply {
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newAdapter
        }

        binding.homeRecyclerviewRecommend.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = recommendAdapter
        }

    }

    override fun onItemClothImageClick(id: Int, position: Int) {
        val intent = Intent(getActivity(), ClothActivity::class.java)
        intent.putExtra("cloth_id", id)
        startActivity(intent)
    }

    override fun onItemStoreNameClick(id: Int, position: Int) {
        val intent = Intent(getActivity(), StoreActivity::class.java)
        intent.putExtra("store_id", id)
        startActivity(intent)
    }

    override fun onItemClothFavoriteClick(like: Boolean, id: Int, view: View, position: Int) {
        if (id != 0) {
            dressViewModel.set_dress_like_data(UpdateDressLikeDto(id))
            dressViewModel.get_dress_like_data()
        }
    }

    private fun initSlideView() {
        imageList = ArrayList()
        imageList.add(R.drawable.slider_home1)
        imageList.add(R.drawable.slider_home2)
        imageList.add(R.drawable.slider_home3)
        imageList.add(R.drawable.slider_home4)
        imageList.add(R.drawable.slider_home5)
        lateinit var sliderView: SliderView
        sliderView = binding.slider
        lateinit var sliderAdapter: SliderAdapter
        sliderAdapter = SliderAdapter(imageList)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }

}