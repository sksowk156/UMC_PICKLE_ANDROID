package com.example.myapplication.view.search.result


import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheetFragment
import com.example.myapplication.databinding.FragmentCategoryBinding
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.OptionViewModel
import com.example.myapplication.widget.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment :
    BaseBottomSheetFragment<FragmentCategoryBinding>(R.layout.fragment_category) {
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()
    private lateinit var optionViewModel: OptionViewModel

    override fun init() {
        optionViewModel =
            ViewModelProvider(requireActivity()).get(OptionViewModel::class.java)
        binding.categoryvm = optionViewModel

        optionViewModel.apply {
            category_bt_event.observe(this@CategoryFragment, EventObserver {
                it as TextView
                set_category_data(it.text.toString())
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

    override fun savedatainit() {}

}