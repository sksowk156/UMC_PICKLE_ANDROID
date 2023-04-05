package com.example.myapplication.view.search.result

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchresultBinding
import com.example.myapplication.data.remote.model.UpdateDressLikeDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.remote.model.search.SearchHistroyData
import com.example.myapplication.view.search.SearchhistoryAdapter
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.view.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.OptionViewModel
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.widget.utils.*
import com.example.myapplication.widget.utils.Utils.KEY_SEARCH_HISTORY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchresultFragment : BaseFragment<FragmentSearchresultBinding>(R.layout.fragment_searchresult) {
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()
    private lateinit var optionViewModel: OptionViewModel
    @Inject
    lateinit var sharedPreferencesmanager: SharedPreferencesManager

    private lateinit var searchresultAdapter: SearchresultAdapter
    private lateinit var searchhistoryAdapter: SearchhistoryAdapter

    private var searchHistoryDataList = ArrayList<SearchHistroyData>()
    private lateinit var resultlayout: ConstraintLayout
    private lateinit var recordlayout: ConstraintLayout
    private var buttonClick = false

    override fun init() {
        optionViewModel = ViewModelProvider(requireActivity()).get(OptionViewModel::class.java)

        binding.searchresultTextviewSort.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment = SortFragment()
            bottomSheetDialogFragment.show(parentFragmentManager, null)
        }

        binding.searchresultTextviewCategory.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment = CategoryFragment()
            bottomSheetDialogFragment.show(parentFragmentManager, null)
        }

        optionViewModel.category_data.observe(this, Observer {
            binding.searchresultTextviewCategory.text = optionViewModel.category_data.value
        })

        optionViewModel.sort_data.observe(this, Observer {
            binding.searchresultTextviewSort.text = optionViewModel.sort_data.value
        })

        optionViewModel.searchword_data.observe(this, Observer { // EditText에 변화가 있을 때
            optionViewModel.initOptiondata() // 옵션 설정을 초기화 한다.

            if(it.isNullOrEmpty()){ // 글자를 다 지운 경우
                searchresultAdapter.submitList(null) // 검색 결과 지우기
                binding.searchresultTextviewResultcount.text = String.format("검색 결과 0개")
                resultlayout.visibility = View.INVISIBLE // 검색 결과 안보이게 하기

                recordlayout.visibility = View.VISIBLE // 검색 기록 보여주기
            }
        })

        optionViewModel.search_bt_event.observe(this, EventObserver{
            if(!optionViewModel.searchword_data.value.isNullOrEmpty()){
                // 중복되는 단어는 추가하지 않고 제일 앞으로 위치만 갱신한다.
                if(searchHistoryDataList.contains(SearchHistroyData(optionViewModel.searchword_data.value.toString()))){
                    searchHistoryDataList.remove(SearchHistroyData(optionViewModel.searchword_data.value.toString()))
                }
                searchHistoryDataList.add(0, SearchHistroyData(optionViewModel.searchword_data.value.toString()))
                sharedPreferencesmanager.setsearchhistoryString(KEY_SEARCH_HISTORY,searchHistoryDataList)
                initRecyclerViewRecord() // 검색 기록 갱신한다.
            }
        })

        resultlayout = binding.searchresultInnerlayoutResult
        recordlayout = binding.searchresultInnerlayoutRecord

        initRecyclerViewResult()
        initRecyclerViewRecord()
    }

    private fun initRecyclerViewResult(){
        searchresultAdapter = SearchresultAdapter(clicklistener = (object : ItemCardClickInterface {
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
                }
            }
        }))

        dressViewModel.dress_search_data.observe(viewLifecycleOwner, Observer {
            resultlayout.visibility = View.VISIBLE
            recordlayout.visibility = View.INVISIBLE
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Log.d("whatisthis", "search result data가 없습니다.")
                    searchresultAdapter.submitList(null)
                    binding.searchresultTextviewResultcount.text = String.format("검색 결과 0개")
                }

                is NetworkResult.Success -> {
                    searchresultAdapter.submitList(it.data!!.data)
                    binding.searchresultTextviewResultcount.text = String.format("검색 결과 %d개", (it.data!!.data)!!.size)
                }
            }
        })


        binding.searchresultRecyclerResult.apply {
            layoutManager= GridLayoutManager(this.context,2)
            adapter= searchresultAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        if(buttonClick){

            dressViewModel.get_dress_search_data(
                optionViewModel.category_data.value.toString(),
                optionViewModel.latlng_data.value!!.first,
                optionViewModel.latlng_data.value!!.second,
                optionViewModel.searchword_data.value!!,
                optionViewModel.sort_data.value.toString()
            )
            buttonClick = false
        }
    }

    private fun initRecyclerViewRecord() {
        // 쉐어드에서 데이터 가져오기
        searchHistoryDataList =
            sharedPreferencesmanager.getsearchhistoryString(KEY_SEARCH_HISTORY) as ArrayList<SearchHistroyData>

        // 기록을 보여줄 recycler의 어댑터, 어댑터 클릭 이벤트 처리
        searchhistoryAdapter =
            SearchhistoryAdapter(object : SearchhistoryAdapter.ItemClickListener {
                // recycler 아이템 중 텍스트를 클릭했을 때 -> 해당 텍스트로 재검색
                override fun onTextItemClick(searchhistory: String, position: Int) {
                    optionViewModel.set_searchword_data(searchhistory)

                    // 화면 전환 및 검색 결과 보여주기(API 요청)
                    dressViewModel.get_dress_search_data(
                        optionViewModel.category_data.value.toString(),
                        optionViewModel.latlng_data.value!!.first,
                        optionViewModel.latlng_data.value!!.second,
                        optionViewModel.searchword_data.value!!,
                        optionViewModel.sort_data.value.toString()
                    )
                    optionViewModel.onSearchBTEvent()
                    // 검색 기록 보여주는 창 가리고
                    resultlayout.visibility = View.VISIBLE
                    recordlayout.visibility = View.INVISIBLE
                    optionViewModel.onHistoryBTEvent()
                }

                // recycler 아이템 중 x 이미지를 클릭했을 때 -> 데이터 삭제
                override fun onImageItemClick(view: View, position: Int) {
                    searchHistoryDataList.removeAt(position)
                    // 쉐어드 데이터를 덮어씌우는 것
                    sharedPreferencesmanager.setsearchhistoryString(
                        KEY_SEARCH_HISTORY,
                        searchHistoryDataList
                    )
                    // 데이터 변경 알리기
                    searchhistoryAdapter.notifyDataSetChanged()
                }
            })

        searchhistoryAdapter.userList = searchHistoryDataList
        // 데이터 변경 알리기
        searchhistoryAdapter.notifyDataSetChanged()

        binding.searchresultRecyclerRecord.apply {
            layoutManager = GridLayoutManager(this.context, 3)
            adapter = searchhistoryAdapter
        }
    }

}
