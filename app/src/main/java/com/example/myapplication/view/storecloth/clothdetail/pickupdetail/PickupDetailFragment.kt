package com.example.myapplication.view.storecloth.clothdetail.pickupdetail

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPickupDetailBinding
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.storecloth.clothdetail.ordercomplete.OrderCompleteFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.OrderViewModel
import com.example.myapplication.viewmodel.StoreViewModel
import java.util.*
import androidx.lifecycle.Observer
import com.example.myapplication.data.remote.model.DressDetailDto
import com.example.myapplication.data.remote.model.DressReservationDto
import com.example.myapplication.data.remote.model.StockQuantityDto
import com.example.myapplication.data.remote.model.StoreDetailDto
import com.example.myapplication.data.remote.model.order.ClothOrderData
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.widget.config.EventObserver
import com.example.myapplication.widget.utils.ColorBindingAdapter
import com.example.myapplication.widget.utils.NetworkResult

class PickupDetailFragment :
    BaseFragment<FragmentPickupDetailBinding>(R.layout.fragment_pickup_detail) {
    private lateinit var storeViewModel: StoreViewModel
    private lateinit var dressViewModel: DressViewModel
    private lateinit var orderViewModel: OrderViewModel

    // 매장 정보
    private lateinit var store_detail_data: StoreDetailDto

    // 옷 정보
    private lateinit var dress_detail_data: DressDetailDto

    // 픽업할 옷 리스트 옵션 정보
    private var order_data = ArrayList<ClothOrderData>()
    private var order_cloth_data = ArrayList<StockQuantityDto>()

    // 날짜 정보
    private var date: String? = null

    // 시간 정보
    private var time: String? = null

    // 전체 가격 정보
    private var totalPrice: Int = 0

    // 날자 정보, 시간 정보를 설정 했을 경우
    private var selected_data = arrayListOf<Boolean>(false, false)

    private var orderBT: Boolean = false

    override fun init() {
        initAppbar(binding.pickupdetailToolbar, "픽업 주문하기", true, false)

        storeViewModel = (activity as ClothActivity).storeViewModel
        dressViewModel = (activity as ClothActivity).dressViewModel
        orderViewModel = ViewModelProvider(requireParentFragment()).get(OrderViewModel::class.java)
        binding.ordervm = orderViewModel

        // 옷에 대한 상세 정보
        dress_detail_data = dressViewModel.dress_detail_data.value!!.data!!
        // 매장에 대한 상세 정보
//        storeViewModel.get_store_detail_data(dress_detail_data.store_id, "전체")
        storeViewModel.store_detail_data.observe(this, Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Error -> {
                    Log.d("whatisthis", "PickupDetailFragment store_detail_data 없음")
                }
                is NetworkResult.Success -> {
                    store_detail_data = it.data!!
                    initStore()
                }
            }
        })

        // 주문할 옷에 대한 상세 정보
        order_data = orderViewModel.order_data.value!!

        // 주문할 옷에 대한 목록 정보
        for (i in order_data) {
            order_cloth_data.add(StockQuantityDto(i.count, i.coloridx, i.sizeidx))
        }

        // 주문할 옷의 전체가격
        orderViewModel.get_calculate_order_price()

        initDate()
        initChip()
        initRecyclerView()
        initButton()
    }

    private fun initStore() {
        with(binding) {
            pickupdetailTextviewStorename.text = store_detail_data.store_name
            pickupdetailTextviewAddress.text = store_detail_data.store_address
            pickupdetailTextviewOperationhours.text = store_detail_data.hours_of_operation
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val pickupDetailAdapter = PickupDetailAdapter()
            pickupdetailRecyclerview.adapter = pickupDetailAdapter
            pickupdetailRecyclerview.layoutManager = LinearLayoutManager(context)
            pickupDetailAdapter.userList = order_data
            pickupDetailAdapter.dressDetail = dress_detail_data
            pickupDetailAdapter.notifyDataSetChanged()
        }
    }

    private fun initDate() {
        with(binding) {
            orderViewModel.pickupdate_bt_event.observe(this@PickupDetailFragment, EventObserver {
                val mcurrentTime = Calendar.getInstance()
                val year = mcurrentTime.get(Calendar.YEAR)
                val month = mcurrentTime.get(Calendar.MONTH)
                val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)

                val datePicker = DatePickerDialog(
                    requireContext(),
                    R.style.DatePickerTheme,
                    object : DatePickerDialog.OnDateSetListener {
                        override fun onDateSet(
                            view: DatePicker?,
                            year: Int,
                            month: Int,
                            dayOfMonth: Int
                        ) {
                            if (month + 1 < 10) { // 10월 이상(2자리수)
                                date = "${year}-0${month + 1}-${day}"
                            } else { // 10월 미만(1자리수)
                                date = "${year}-${month + 1}-${day}"
                            }
                            pickupdetailTextviewDate.text = date
                            activateButton(0, date)
                        }
                    },
                    year,
                    month,
                    day
                )

                datePicker.show()
            })
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initChip() {
        orderViewModel.apply {
            pickuptime_bt_event.observe(this@PickupDetailFragment, EventObserver {
                it as TextView
                if (pickuptime_data.value == null) { // 처음 버튼을 누를 때
                    it.setTextColor(
                        ContextCompat.getColor(
                            it.context,
                            R.color.selected_storeoption_text
                        )
                    )
                    it.setBackgroundResource(R.drawable.chip_background_selected)
                    set_pickuptime_data(it)
                    time = pickuptime_data.value!!.text.toString()
                } else { // 두 번째 누를 때부터
                    if (pickuptime_data.value == it) { // 같은 버튼을 누를 때
                        it.setTextColor(
                            ContextCompat.getColor(
                                it.context,
                                R.color.unselected_storeoption_text
                            )
                        )
                        it.setBackgroundResource(R.drawable.chip_background)
                        set_pickuptime_data(null)
                        time = null
                    } else { // 다른 버튼을 누를 때
                        pickuptime_data.value!!.setTextColor(
                            ContextCompat.getColor(
                                it.context,
                                R.color.unselected_storeoption_text
                            )
                        )
                        pickuptime_data.value!!.setBackgroundResource(R.drawable.chip_background)
                        it.setTextColor(
                            ContextCompat.getColor(
                                it.context,
                                R.color.selected_storeoption_text
                            )
                        )
                        it.setBackgroundResource(R.drawable.chip_background_selected)
                        set_pickuptime_data(it)
                        time = pickuptime_data.value!!.text.toString()
                    }
                }
                activateButton(1, time)
            })
        }
    }

    private fun activateButton(optionIdx: Int, optionName: String?) {
        selected_data[optionIdx] = optionName != null
        orderBT = selected_data[0] == true && selected_data[1] == true
        if (orderBT) {
            binding.pickupdetailTextviewReservation.setBackgroundResource(R.drawable.green_button_background)
            binding.pickupdetailTextviewReservation.setTextColor(Color.WHITE)
        } else {
            binding.pickupdetailTextviewReservation.setBackgroundResource(R.drawable.gray_button_background)
            binding.pickupdetailTextviewReservation.setTextColor(Color.parseColor("#A4A4A4"))
        }
    }

    private fun initButton() {
        with(binding) {

            orderViewModel.pickupreservation_bt_event.observe(
                this@PickupDetailFragment,
                EventObserver {
                    if (orderBT) {
                        //예약 정보 보내기
                        var comment = ""
                        if(orderViewModel._order_request_data.value!=null){
                            comment = orderViewModel._order_request_data.value!!
                        }
                        val dress_id = dress_detail_data.dress_id
                        val pickup_datetime = date + " " + time + ":00"
                        val price = totalPrice
                        val reserved_dress_list = order_cloth_data.toList()
                        val store_id = dress_detail_data.store_id

                        val dressReservationDto = DressReservationDto(
                            comment,
                            dress_id,
                            pickup_datetime,
                            price,
                            reserved_dress_list,
                            store_id
                        )
                        dressViewModel.set_dress_reservation(dressReservationDto)

                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.clothblank_layout,
                                OrderCompleteFragment(),
                                "ordercomplete"
                            )
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                })
        }
    }
}