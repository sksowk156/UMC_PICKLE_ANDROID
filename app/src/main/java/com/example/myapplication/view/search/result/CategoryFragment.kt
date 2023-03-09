package com.example.myapplication.view.search.result


import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheetFragment
import com.example.myapplication.databinding.FragmentCategoryBinding
import com.example.myapplication.viewmodel.OptionViewModel
import com.example.myapplication.widget.config.EventObserver

class CategoryFragment :
    BaseBottomSheetFragment<FragmentCategoryBinding>(R.layout.fragment_category) {
    private lateinit var optionViewModel: OptionViewModel

    override fun init() {
        optionViewModel =
            ViewModelProvider(requireActivity()).get(OptionViewModel::class.java)
        binding.categoryvm = optionViewModel

        optionViewModel.apply {
            category_bt_event.observe(this@CategoryFragment, EventObserver {
                it as TextView
                optionViewModel.set_category_data(it.text.toString())
                dismiss()
            })
        }
    }

    override fun savedatainit() {}

}