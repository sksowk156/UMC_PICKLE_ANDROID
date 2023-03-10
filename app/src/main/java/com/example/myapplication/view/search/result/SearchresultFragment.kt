package com.example.myapplication.view.search.result

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchresultBinding
import com.example.myapplication.data.remote.model.UpdateDressLikeDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.search.SearchActivity
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.view.storecloth.storedetail.StoreActivity
import com.example.myapplication.viewmodel.OptionViewModel
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.SearchViewModel
import com.example.myapplication.widget.utils.ItemCardClickInterface
import com.example.myapplication.widget.utils.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchresultFragment : BaseFragment<FragmentSearchresultBinding>(R.layout.fragment_searchresult) {
    private lateinit var dressViewModel: DressViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var optionViewModel: OptionViewModel

    private lateinit var fragmentadapter: SearchresultAdapter
    override fun init() {
        dressViewModel = (activity as SearchActivity).dressViewModel
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
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

        initRecyclerView()
    }

    private fun initRecyclerView(){
        fragmentadapter = SearchresultAdapter(clicklistener = (object : ItemCardClickInterface {
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
                }
            }
        }))

        dressViewModel.dress_search_data.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Log.d("whatisthis", "search result data가 없습니다.")
                    fragmentadapter.submitList(null)
                    binding.searchresultTextviewResultcount.text = String.format("검색 결과 0개")
                }

                is NetworkResult.Success -> {
                    fragmentadapter.submitList(it.data!!.data)
                    binding.searchresultTextviewResultcount.text = String.format("검색 결과 %d개", (it.data!!.data)!!.size)
                }
            }
        })


        binding.searchresultRecycler.apply {
            layoutManager= GridLayoutManager(this.context,2)
            adapter= fragmentadapter
        }

    }
}
