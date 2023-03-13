package com.example.myapplication.view.search

import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySearchBinding
import com.example.myapplication.data.remote.model.search.SearchHistroyData
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.view.search.result.SearchresultFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.OptionViewModel
import com.example.myapplication.widget.utils.EventObserver
import com.example.myapplication.widget.utils.SharedPreferencesManager
import com.example.myapplication.widget.utils.Utils.KEY_SEARCH_HISTORY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    val dressViewModel: DressViewModel by viewModels<DressViewModel>()

    @Inject
    lateinit var sharedPreferencesmanager: SharedPreferencesManager
    private lateinit var optionViewModel: OptionViewModel
    private var deleteButton = false

    // 검색 기록을 저장하는 배열
    private var searchHistoryDataList = ArrayList<SearchHistroyData>()

    override fun savedatainit() {
        super.savedatainit()
        supportFragmentManager
            .beginTransaction()
            .add(binding.searchLayoutResult.id, SearchresultFragment(), "searchresult")
            .commitAllowingStateLoss()
    }

    override fun init() {
        optionViewModel = ViewModelProvider(this).get(OptionViewModel::class.java)
        binding.optionvm = optionViewModel

        // 위치 정보 갱신하기
        getLocation()
        optionViewModel.set_latlng_data(lat_lng!!)

        // 뒤로가기 버튼 이벤트 처리
        binding.searchImageviewBack.setOnClickListener {
            finish()
        }

        // EditText 커서 유무 이벤트 처리
        binding.searchEditQeury.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) { // 커서가 있을 때
                changeSearchButton(false)
                if (optionViewModel.searchword_data.value.isNullOrEmpty()) { // editText에 글자가 없다면 -> 기록 보여주기

                }

            } else { // 커서가 없을 때
                if (!optionViewModel.searchword_data.value.isNullOrEmpty()) { // editText에 글자가 있다면
                    changeSearchButton(true)

                } else { // editText에 글자가 입력되어 없다면
                    changeSearchButton(false)
                }
            }
        }

        // 검색 기록 클릭 시
        optionViewModel.history_bt_event.observe(this, EventObserver {
            if (!optionViewModel.searchword_data.value.isNullOrEmpty()) { // editText에 글자가 입력되어 있다면
                changeSearchButton(true)
            }
        })

        // EditText 엔터 이벤트 처리
        binding.searchEditQeury.setOnEditorActionListener { v, actionId, event -> // 키보드에서 엔터 키 이벤트 처리
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchNclear()

            }
            handled
        }

        // 오른쪽 아이콘 이벤트 처리
        binding.searchImageviewSearchordelete.setOnClickListener {
            if (deleteButton) { // editText 에서 x 버튼으로 입력 글자 지우기
                optionViewModel.set_searchword_data("")
                binding.searchEditQeury.requestFocus()
            } else { // ediText 에서 검색 버튼으로 입력 글자 검색하기
                searchNclear()
            }
        }
    }

    // 검색실행(엔터 혹은 버튼 클릭) 후 검색 결과 보여주고, 키보드 내리기
    private fun searchNclear() {
        dressViewModel.get_dress_search_data(
            optionViewModel.category_data.value.toString(),
            optionViewModel.latlng_data.value!!.first,
            optionViewModel.latlng_data.value!!.second,
            optionViewModel.searchword_data.value!!,
            optionViewModel.sort_data.value.toString()
        )
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditQeury.windowToken, 0)
        binding.searchEditQeury.clearFocus()
        optionViewModel.onSearchBTEvent()
    }

    // 오른쪽 아이콘 변경해주는 메소드(검색 or x)
    private fun changeSearchButton(on:Boolean){
        if(on){
            binding.searchImageviewSearchordelete
            Glide.with(this)
                .load(R.drawable.icon_searchdelete) //이미지
                .into(binding.searchImageviewSearchordelete) //보여줄 위치
            deleteButton = true // 오른쪽 아이콘 모양은 x 모양으로
        }else{
            binding.searchImageviewSearchordelete
            Glide.with(this)
                .load(R.drawable.icon_search) //이미지
                .into(binding.searchImageviewSearchordelete) //보여줄 위치
            deleteButton = false // 오른쪽 아이콘 모양은 검색 모양으로
        }
    }
}


