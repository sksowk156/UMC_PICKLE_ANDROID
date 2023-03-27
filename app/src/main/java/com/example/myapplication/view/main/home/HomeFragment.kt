package com.example.myapplication.view.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.data.remote.model.*
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.view.main.home.newclothe.NewFragment
import com.example.myapplication.view.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.view.main.home.recent.RecentFragment
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.view.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.*
import com.example.myapplication.widget.utils.EventObserver
import com.example.myapplication.widget.utils.ItemCardClickInterface
import com.example.myapplication.widget.utils.NetworkResult
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    ItemCardClickInterface {
    val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()
    val userViewModel: UserViewModel by activityViewModels<UserViewModel>()

    private lateinit var recentAdapter: HomeRecentAdapter
    private lateinit var newAdapter: HomeNewAdapter
    private lateinit var recommendAdapter: HomeRecommendAdapter

    private lateinit var imageList: ArrayList<Int>
    private var buttonClick : Boolean = false

    override fun init() {
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

        // 좋아요 갱신
        dressViewModel.dress_like_data.observe(this@HomeFragment, Observer {
            when(it){
                is NetworkResult.Loading ->{

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Success ->{
                    homeViewModel.get_home_data(
                        homeViewModel.home_latlng.value!!.first,
                        homeViewModel.home_latlng.value!!.second)
                }
            }
        })
    }

    // 의상 상세 정보(activity)에서 돌아왔을 때 작동
    override fun onResume() {
        super.onResume()
        if(buttonClick){
            homeViewModel.get_home_data(homeViewModel.home_latlng.value!!.first, homeViewModel.home_latlng.value!!.second)
            buttonClick = false
        }
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
//                    recommendAdapter.notifyDataSetChanged()
                    newAdapter.submitList(it.data.newDresses!!.toMutableList())
//                    newAdapter.notifyDataSetChanged()
                    recommendAdapter.submitList(it.data.recDresses!!.toMutableList())
//                    recommendAdapter.notifyDataSetChanged()
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