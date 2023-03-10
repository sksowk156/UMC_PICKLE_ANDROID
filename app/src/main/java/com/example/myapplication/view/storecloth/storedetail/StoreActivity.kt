package com.example.myapplication.view.storecloth.storedetail

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityStoreBinding
import com.example.myapplication.data.remote.model.UpdateDressLikeDto
import com.example.myapplication.data.remote.model.UpdateStoreLikeDto
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.repository.DressRepository
import com.example.myapplication.repository.HomeRepository
import com.example.myapplication.repository.StoreRepository
import com.example.myapplication.view.search.SearchActivity
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.viewmodel.*
import com.example.myapplication.viewmodel.factory.HomeViewModelFactory
import com.example.myapplication.viewmodel.factory.StoreViewModelFactory
import com.example.myapplication.widget.utils.EventObserver
import com.example.myapplication.widget.utils.ItemCardClickInterface
import com.example.myapplication.widget.utils.NetworkResult

class StoreActivity : BaseActivity<ActivityStoreBinding>(R.layout.activity_store),
    ItemCardClickInterface {
    private lateinit var storeViewModel: StoreViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var dressViewModel: DressViewModel
    private lateinit var optionViewModel: OptionViewModel

    lateinit var storedetailAdapter: StoreDetailAdapter
    private var store_id : Int ?=null

    private lateinit var toolbar: Toolbar
    private var store_like_state: Boolean? = false
    private var storeIdData: Int? = null
    private var lat_lng: Pair<Double, Double> = Pair(37.5581, 126.9260)

    override fun init() {
        val dressRepository = DressRepository()
        val storeRepository = StoreRepository()
        val homeRepository = HomeRepository()
        val dressViewModelProviderFactory = DressViewModelFactory(dressRepository)
        val storeViewModelProviderFactory = StoreViewModelFactory(storeRepository)
        val homeViewModelProviderFactory = HomeViewModelFactory(homeRepository)
        // 뷰모델 선언
        dressViewModel = ViewModelProvider(this, dressViewModelProviderFactory).get(DressViewModel::class.java)
        storeViewModel = ViewModelProvider(this, storeViewModelProviderFactory).get(StoreViewModel::class.java)
        homeViewModel = ViewModelProvider(this, homeViewModelProviderFactory).get(HomeViewModel::class.java)
        optionViewModel = ViewModelProvider(this).get(OptionViewModel::class.java)

        binding.clothkindvm = optionViewModel
        store_id = intent.getIntExtra("store_id", 0)
        storeViewModel.get_store_detail_data(store_id!!, binding.chip1.text.toString())

        // lat, lng 정보를 얻기 위해서
        getLocation()
        homeViewModel.home_latlng.observe(this, Observer {
            lat_lng = it
        })

        storedetailAdapter = StoreDetailAdapter(this)

        // 매장 상세 정보 요청
        storeViewModel.store_detail_data.observe(this, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Error -> {
                    Log.d("whatisthis", "StoreActivity : 데이터없음")
                }
                is NetworkResult.Success -> {
                    Glide.with(this)
                        .load(it.data!!.store_image_url) //이미지
                        .into(binding.storeImageviewImage) //보여줄 위치

                    if (it.data!!.is_liked!!) {
                        Glide.with(this)
                            .load(R.drawable.icon_favorite_filledpink) //이미지
                            .into(binding.storeImageviewFavorite) //보여줄 위치
                    } else {
                        Glide.with(this)
                            .load(R.drawable.icon_favorite_line) //이미지
                            .into(binding.storeImageviewFavorite)  //보여줄 위치
                    }

                    binding.storeTextviewStorename.text = it.data!!.store_name
                    binding.storeTextviewAddress.text = it.data!!.store_address
                    binding.storeTextviewOperationhours.text = it.data!!.hours_of_operation

                    storeIdData = it.data!!.storeId
                    store_like_state = it.data!!.is_liked

                    storedetailAdapter.submitList(it.data!!.store_dress_list?.toMutableList())
                }
            }
        })

        // 어댑터 연결
        binding.storeRecyclerview.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = storedetailAdapter
        }

        binding.storeImageviewFavorite.setOnClickListener {
            // 임시로 이미지만 변경 -> store_detail_data를 전부 다시 요청하면 너무 비효율적
            if (store_like_state == true) {
                Glide.with(this)
                    .load(R.drawable.icon_favorite_line) //이미지
                    .into(binding.storeImageviewFavorite)  //보여줄 위치
                store_like_state = false
            } else {
                Glide.with(this)
                    .load(R.drawable.icon_favorite_filledpink) //이미지
                    .into(binding.storeImageviewFavorite)  //보여줄 위치
                store_like_state = true
            }
            storeViewModel.set_store_like_data(UpdateStoreLikeDto(false, storeIdData!!))
        }

        binding.storeRecyclerview.run {
            val spanCount = 2
            val space = 30
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }
        // 앱바 설정
        initAppbar(R.menu.menu_appbar)
        initChip()
    }

    private fun initAppbar(menuRes: Int) {
        toolbar = binding.toolbar.toolbarToolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(menuRes, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> { // 뒤로가기 버튼을 눌렀을 때
                        finish()
                        true
                    }
                    R.id.search -> {
                        val intent = Intent(this@StoreActivity, SearchActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.notification -> { // 알림 버튼을 눌렀을 때
                        true
                    }
                    else -> false
                }
            }
        })
    }

    override fun onItemClothImageClick(id: Int, position: Int) {
        val intent = Intent(this, ClothActivity::class.java)
        intent.putExtra("cloth_id", id)
        startActivity(intent)
    }

    override fun onItemStoreNameClick(id: Int, position: Int) {
    }

    // 옷 좋아요 클릭 시
    override fun onItemClothFavoriteClick(like: Boolean, id: Int, view: View, position: Int) {
        if (id != 0) {
            dressViewModel.set_dress_like_data(UpdateDressLikeDto(id))
        }
    }

    private fun initChip() {
        optionViewModel.apply {
            clothkind_bt_event.observe(this@StoreActivity, EventObserver {
                if(clothkind_data.value==null){
                    optionViewModel.set_clothkind_data(binding.chip1) // 초기값
                }
                it as TextView

                if(it!=clothkind_data.value!!){
                    clothkind_data.value!!.setTextColor(ContextCompat.getColor(it.context, R.color.unselected_storeoption_text))
                    clothkind_data.value!!.setBackgroundResource(R.drawable.chip_background)
                    it.setTextColor(ContextCompat.getColor(it.context, R.color.selected_storeoption_text))
                    it.setBackgroundResource(R.drawable.chip_background_selected)
                }
                optionViewModel.set_clothkind_data(it)

                storeViewModel.get_store_detail_data(store_id!!, it.text.toString())
            })
        }
    }
}