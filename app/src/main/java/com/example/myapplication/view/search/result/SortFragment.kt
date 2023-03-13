package com.example.myapplication.view.search.result

import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheetFragment
import com.example.myapplication.databinding.FragmentSortBinding
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.OptionViewModel
import com.example.myapplication.widget.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment : BaseBottomSheetFragment<FragmentSortBinding>(R.layout.fragment_sort) {
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()
    private lateinit var optionViewModel: OptionViewModel

    override fun init() {
        optionViewModel =
            ViewModelProvider(requireActivity()).get(OptionViewModel::class.java)
        binding.sortvm = optionViewModel


        optionViewModel.apply {
            sort_bt_event.observe(this@SortFragment, EventObserver {
                it as TextView
                set_sort_data(it.text.toString())
                dressViewModel.get_dress_search_data(
                    category_data.value.toString(),
                    latlng_data.value!!.first,
                    latlng_data.value!!.second,
                    searchword_data.value!!.toString(),
                    sort_data.value.toString()
                )
                dismiss()
            })
        }
    }

}