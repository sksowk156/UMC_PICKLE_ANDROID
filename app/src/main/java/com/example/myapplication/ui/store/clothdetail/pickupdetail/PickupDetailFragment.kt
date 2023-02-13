package com.example.myapplication.ui.store.clothdetail.pickupdetail


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R

import com.example.myapplication.databinding.FragmentPickupDetailBinding
import com.example.myapplication.db.remote.model.DressDetailDto
import com.example.myapplication.db.remote.model.DressReservationDto
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.location.map.MapFragment
import com.example.myapplication.ui.main.profile.orderstatus.OrderedClotheData
import com.example.myapplication.ui.store.clothdetail.ordercomplete.OrderCompleteFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.ReservationViewModel
import java.util.*
import kotlin.collections.ArrayList


class PickupDetailFragment : BaseFragment<FragmentPickupDetailBinding>(R.layout.fragment_pickup_detail) {
    var chipGroup = ArrayList<TextView>()
    lateinit var inputDate : String
    lateinit var reservationViewModel: ReservationViewModel

    override fun init() {
        reservationViewModel = ViewModelProvider(requireActivity()).get(ReservationViewModel::class.java)


        initRecyclerView()
        initDatePicker()
        initChip()
    }

    private fun initRecyclerView() {
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val pickupDetailAdapter = PickupDetailAdapter()

            val pickupDetailDatalist: ArrayList<OrderedClotheData> = ArrayList()
            pickupDetailDatalist.add(
                OrderedClotheData(
                    "20200204",
                    R.drawable.cardigan1,
                    "ㄴㅁㅇ",
                    "옷",
                    "8700",
                    "검정",
                    "M"
                )
            )
            pickupDetailDatalist.add(
                OrderedClotheData(
                    "20200204",
                    R.drawable.cardigan1,
                    "ㄴㅁㅇ",
                    "옷",
                    "8700",
                    "검정",
                    "M"
                )
            )
            pickupDetailDatalist.add(
                OrderedClotheData(
                    "20200204",
                    R.drawable.cardigan1,
                    "ㄴㅁㅇ",
                    "옷",
                    "8700",
                    "검정",
                    "M"
                )
            )

            pickupdetailRecyclerview.adapter = pickupDetailAdapter
            pickupdetailRecyclerview.layoutManager = LinearLayoutManager(context)
            pickupDetailAdapter.userList = pickupDetailDatalist
            pickupDetailAdapter.notifyDataSetChanged()
        }
    }

    private fun initDatePicker(){
        binding.tvDateDialog.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                binding.tvDateDialog.text = "${year}. ${month+1}. ${day}"
                inputDate = "${year}-${month+1}-${day}"
            }
            DatePickerDialog(requireContext(), data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initChip() {
        chipGroup.add(binding.chip1)
        chipGroup.add(binding.chip2)
        chipGroup.add(binding.chip3)
        chipGroup.add(binding.chip4)
        chipGroup.add(binding.chip5)
        chipGroup.add(binding.chip6)
        chipGroup.add(binding.chip7)
        chipGroup.add(binding.chip8)
        chipGroup.add(binding.chip9)
        chipGroup.add(binding.chip10)
        chipGroup.add(binding.chip11)
        chipGroup.add(binding.chip12)
        chipGroup.add(binding.chip13)
        chipGroup.add(binding.chip14)
        chipGroup.add(binding.chip15)
        chipGroup.add(binding.chip16)

        for (i in 0..chipGroup.size - 1) {
            chipGroup[i].setOnClickListener {
                for (j in 0..chipGroup.size - 1) {
                    if (chipGroup[j].background.constantState == resources.getDrawable(R.drawable.chip_background_selected).constantState) {
                        chipGroup[j].setBackgroundResource(R.drawable.chip_background)
                        chipGroup[j].setTextColor(Color.BLACK)
                    }
                    chipGroup[i].setBackgroundResource(R.drawable.chip_background_selected)
                    chipGroup[i].setTextColor(Color.WHITE)
                    binding.ivOrder.setBackgroundResource(R.drawable.green_button_background)
                    binding.ivOrder.setTextColor(Color.WHITE)
                    binding.ivOrder.setOnClickListener{
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.clothblank_layout, OrderCompleteFragment(), "ordercomplete")
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                }
            }
        }
    }

}