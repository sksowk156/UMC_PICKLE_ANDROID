package com.example.myapplication.view.search.result

import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheetFragment
import com.example.myapplication.databinding.FragmentSortBinding
import com.example.myapplication.viewmodel.OptionViewModel
import com.example.myapplication.widget.config.EventObserver

class SortFragment : BaseBottomSheetFragment<FragmentSortBinding>(R.layout.fragment_sort) {
    private lateinit var optionViewModel: OptionViewModel

    override fun init() {
        optionViewModel =
            ViewModelProvider(requireActivity()).get(OptionViewModel::class.java)
        binding.sortvm = optionViewModel


        optionViewModel.apply {
            sort_bt_event.observe(this@SortFragment, EventObserver {
                it as TextView
                optionViewModel.set_sort_data(it.text.toString())
                dismiss()
            })
        }
    }

}