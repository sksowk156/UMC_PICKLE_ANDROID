package com.example.myapplication.ui.search.result

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchresultBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.home.recent.HomeRecommendAdapter
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.SearchViewModel

class SearchresultFragment : BaseFragment<FragmentSearchresultBinding>(R.layout.fragment_searchresult) {
    private lateinit var dressViewModel: DressViewModel
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var fragmentadapter: HomeRecommendAdapter

    private var selectedcategory : String ?= null
    private var selectedsort : String ?= null

    override fun init() {
//        dressViewModel = ViewModelProvider(requireActivity()).get(DressViewModel::class.java)
//        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
//
//        binding.searchresultTextviewCategory
//
//        binding.searchresultTextviewSort
//
//        dressViewModel.get_dress_search_data(category,lat,lng,name,sort)
//        initRecyclerView()
    }

//    private fun initRecyclerView(){
//        fragmentadapter = HomeRecommendAdapter(clicklistener = (object : ItemCardClickInterface {
//            override fun onItemClothImageClick(id:Int, position: Int) {
//                val intent = Intent(context, ClothActivity::class.java)
//                intent.putExtra("storeName","store1")
//                intent.putExtra("clothName","옷1")
//                intent.putExtra("clothPrice",30000)
//                //intent.putExtra("StoreName",20000)
//
//                startActivity(intent)
//            }
//
//            override fun onItemStoreNameClick(id:Int, position: Int) {
//                val intent = Intent(getActivity(), StoreActivity::class.java)
//                startActivity(intent)
//            }
//
//            override fun onItemClothFavoriteClick(like:Boolean,id:Int, view : View, position: Int) {
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
