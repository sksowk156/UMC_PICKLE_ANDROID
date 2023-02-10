package com.example.myapplication.ui.main.home

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.db.remote.model.HomeModel
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.ItemClickInterface
import com.example.myapplication.ui.main.home.newclothe.NewFragment
import com.example.myapplication.ui.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.ui.main.home.recent.RecentFragment
import com.example.myapplication.ui.store.clothdetail.ClothActivity
import com.example.myapplication.ui.store.storedetail.StoreActivity
import com.example.myapplication.viewmodel.HomeViewModel
import com.smarteist.autoimageslider.SliderView

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), ItemClickInterface {
    lateinit var homeViewModel: HomeViewModel

    lateinit var recentAdapter: HomeRecentAdapter
    lateinit var newAdapter: HomeNewAdapter
    lateinit var recommendAdapter: HomeRecommendAdapter

    lateinit var imageList: ArrayList<Int>

    override fun init() {
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        initSlideView()
        initRecyclerView()
        binding.button.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .add(R.id.home_base_layout, RecentFragment(), "recent")
                .addToBackStack(null)
                .commitAllowingStateLoss()

        }

        binding.button2.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.home_base_layout, NewFragment(), "new")
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }


    private fun initSlideView() {
        imageList = ArrayList()
        imageList.add(R.drawable.img_2)
        imageList.add(R.drawable.img_3)
        imageList.add(R.drawable.img_4)
        imageList.add(R.drawable.img_5)
        imageList.add(R.drawable.img_6)
        // on below line we are creating
        // a variable for our slider view.
        lateinit var sliderView: SliderView
        sliderView = binding.slider
        // on below line we are creating
        // a variable for our slider adapter.
        lateinit var sliderAdapter: SliderAdapter
        sliderAdapter = SliderAdapter(imageList)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        // on below line we are setting adapter for our slider.
        sliderView.setSliderAdapter(sliderAdapter)
        // on below line we are setting scroll time
        // in seconds for our slider view.
        sliderView.scrollTimeInSec = 3
        // on below line we are setting auto cycle
        // to true to auto slide our items.
        sliderView.isAutoCycle = true
        // on below line we are calling start
        // auto cycle to start our cycle.
        sliderView.startAutoCycle()
    }

    private fun initRecyclerView() {
        recentAdapter = HomeRecentAdapter(this@HomeFragment)
        newAdapter = HomeNewAdapter(this@HomeFragment)
        recommendAdapter = HomeRecommendAdapter(this@HomeFragment)

        homeViewModel.home_data.observe(viewLifecycleOwner, Observer<HomeModel> { now_homeModel ->
            if (now_homeModel != null) {
                recentAdapter.submitList(now_homeModel.recentView?.toMutableList())
                newAdapter.submitList(now_homeModel.newDresses?.toMutableList())
                recommendAdapter.submitList(now_homeModel.recDresses?.toMutableList())
            } else {
                Log.d("whatisthis", "11네트워크 오류가 발생했습니다.")
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

    override fun onItemImageClick(id: Int, position: Int) {
        val intent = Intent(getActivity(), ClothActivity::class.java)
        intent.putExtra("cloth_id", id)

        startActivity(intent)
    }

    override fun onItemStoreNameClick(id: Int, position: Int) {
        val intent = Intent(getActivity(), StoreActivity::class.java)
        intent.putExtra("store_id", id)

        startActivity(intent)
    }

    override fun onItemButtonClick(id: Int, position: Int) {
        showToast("clicked!!")
    }

    override fun onItemFavoriteClick(id: Int, position: Int) {
//        if(view == view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)){
//            if (clothesList[position].like == false) {
//                //화면에 보여주기
//                Glide.with(this@HomeFragment)
//                    .load(R.drawable.icon_favorite_filledpink) //이미지
//                    .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
//                clothesList[position].like = true
//            } else {
//                //화면에 보여주기
//                Glide.with(this@HomeFragment)
//                    .load(R.drawable.icon_favorite_whiteline) //이미지
//                    .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
//                clothesList[position].like = false
//            }
//        }else{
//            if (newclothesList[position].like == false) {
//                //화면에 보여주기
//                Glide.with(this@HomeFragment)
//                    .load(R.drawable.icon_favorite_filledpink) //이미지
//                    .into(view.findViewById<ImageButton>(R.id.homecard_imagebutton_favorite)) //보여줄 위치
//                newclothesList[position].like = true
//            } else {
//                //화면에 보여주기
//                Glide.with(this@HomeFragment)
//                    .load(R.drawable.icon_favorite_whiteline) //이미지
//                    .into(view.findViewById<ImageButton>(R.id.homecard_imagebutton_favorite)) //보여줄 위치
//                newclothesList[position].like = false
//            }
//        }
    }
}