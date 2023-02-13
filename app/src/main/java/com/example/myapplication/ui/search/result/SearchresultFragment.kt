package com.example.myapplication.ui.search.result

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchresultBinding
import com.example.myapplication.ui.ItemCardClickInterface
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.ui.search.CategoryFragment
import com.example.myapplication.ui.search.SortFragment
import com.example.myapplication.ui.storecloth.clothdetail.ClothActivity
import com.example.myapplication.ui.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchresultFragment : BaseFragment<FragmentSearchresultBinding>(R.layout.fragment_searchresult) {
    private lateinit var dressViewModel: DressViewModel
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var fragmentadapter: HomeRecommendAdapter

    private var selectedcategory : String ?= null
    private var selectedsort : String ?= null

    override fun init() {
        dressViewModel = ViewModelProvider(requireActivity()).get(DressViewModel::class.java)
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)

        binding.searchresultTextviewSort.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment = SortFragment()
            bottomSheetDialogFragment.show(parentFragmentManager, null)
        }

        binding.searchresultTextviewCategory.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment = CategoryFragment()
            bottomSheetDialogFragment.show(parentFragmentManager, null)
        }

//        dressViewModel.get_dress_search_data(category,lat,lng,name,sort)
//        initRecyclerView()
    }

//    private fun initRecyclerView(){
//        fragmentadapter = HomeRecommendAdapter(clickInterface = (object : ItemCardClickInterface {
//            override fun onItemClothImageClick(id:Int, position: Int) {
//                val intent = Intent(context, ClothActivity::class.java)
//                intent.putExtra("cloth_id", id)
//                startActivity(intent)
//            }
//
//            override fun onItemStoreNameClick(id:Int, position: Int) {
//                val intent = Intent(getActivity(), StoreActivity::class.java)
//                startActivity(intent)
//            }
//
//            override fun onItemClothFavoriteClick(like:Boolean, id:Int, view : View, position: Int) {
//                if (clothesList[position].like == false) {
//                    //화면에 보여주기
//                    Glide.with(this@SearchresultFragment)
//                        .load(R.drawable.icon_favorite_filledpink) //이미지
//                        .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
//                    clothesList[position].like = true
//                } else {
//                    //화면에 보여주기
//                    Glide.with(this@SearchresultFragment)
//                        .load(R.drawable.icon_favorite_whiteline) //이미지
//                        .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
//                    clothesList[position].like = false
//                }
//            }
//        }))
//        fragmentadapter.submitList(clothesList.toMutableList())
//        binding.searchresultRecycler.apply {
//            layoutManager= GridLayoutManager(this.context,2)
//            adapter= fragmentadapter
//        }
//
//        binding.searchresultTextviewResultcount.text = String.format("검색 결과 %d개", clothesList.size)
//    }
}
