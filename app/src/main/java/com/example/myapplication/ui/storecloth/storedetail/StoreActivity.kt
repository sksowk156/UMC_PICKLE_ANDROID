package com.example.myapplication.ui.storecloth.storedetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityStoreBinding
import com.example.myapplication.db.remote.model.StoreDetailDto
import com.example.myapplication.ui.base.BaseActivity
import com.example.myapplication.ui.main.ItemClickInterface
import com.example.myapplication.ui.search.SearchActivity
import com.example.myapplication.ui.storecloth.clothdetail.ClothActivity
import com.example.myapplication.ui.storecloth.clothdetail.ordercomplete.OrderCompleteFragment
import com.example.myapplication.viewmodel.StoreViewModel
import kotlinx.android.synthetic.main.toolbar_content.view.*


class StoreActivity : BaseActivity<ActivityStoreBinding>(R.layout.activity_store), ItemClickInterface {
    var chipGroup = ArrayList<TextView>()
    lateinit var storeViewModel: StoreViewModel
    lateinit var storedetailAdapter: StoreDetailAdapter
    private lateinit var toolbar: Toolbar

    override fun init() {
        // 뷰모델 선언
        storeViewModel = ViewModelProvider(this).get(StoreViewModel::class.java)
        storeViewModel.get_store_detail_data(intent.getIntExtra("store_id",0),"전체")

        storedetailAdapter = StoreDetailAdapter(this)

        // 매장 상세 정보 요청
        storeViewModel.store_detail_data.observe(this, Observer<StoreDetailDto> { now_storedetail ->
            if (now_storedetail != null) {
                Glide.with(this)
                    .load(now_storedetail.store_image_url) //이미지
                    .into(binding.storeImageviewImage) //보여줄 위치

                binding.storeTextviewStorename.text = now_storedetail.store_name
                binding.storeTextviewAddress.text = now_storedetail.store_address
                binding.storeTextviewOperationhours.text = now_storedetail.hours_of_operation
                storedetailAdapter.submitList(now_storedetail.store_dress_list?.toMutableList())
            } else {
                Log.d("whatisthis", "store_detail_data, 데이터 없음")
            }
        })

        // 어댑터 연결
        binding.storeRecyclerview.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = storedetailAdapter
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

    private fun initAppbar(menuRes : Int){
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

    override fun onItemImageClick(id: Int, position: Int) {
        val intent = Intent(this, ClothActivity::class.java)
        intent.putExtra("cloth_id", id)
        startActivity(intent)
    }

    override fun onItemStoreNameClick(id: Int, position: Int) {
    }

    override fun onItemFavoriteClick(id: Int, position: Int) {
        // 좋아요 정보 갱신

    }

    @SuppressLint("ResourceAsColor")
    private fun initChip() {
        chipGroup.add(binding.chip1)
        chipGroup.add(binding.chip2)
        chipGroup.add(binding.chip3)
        chipGroup.add(binding.chip4)
        chipGroup.add(binding.chip5)
        chipGroup.add(binding.chip6)

        for (i in 0..chipGroup.size - 1) {
            chipGroup[i].setOnClickListener {
                for (j in 0..chipGroup.size - 1) {
                    if (chipGroup[j].background.constantState == resources.getDrawable(R.drawable.chip_background_selected).constantState) {
                        chipGroup[j].setBackgroundResource(R.drawable.chip_background)
                        chipGroup[j].setTextColor(Color.BLACK)
                    }
                    chipGroup[i].setBackgroundResource(R.drawable.chip_background_selected)
                    chipGroup[i].setTextColor(Color.WHITE)
                }
            }
        }
    }

}