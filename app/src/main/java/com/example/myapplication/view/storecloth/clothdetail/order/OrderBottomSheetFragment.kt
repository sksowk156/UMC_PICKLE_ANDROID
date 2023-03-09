package com.example.myapplication.view.storecloth.clothdetail.order

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheetFragment
import com.example.myapplication.databinding.FragmentOrderBinding
import com.example.myapplication.data.remote.model.order.ClothOptionData
import com.example.myapplication.view.main.profile.orderstatus.OrderListDivider
import com.example.myapplication.data.remote.model.order.ClothOrderData
import com.example.myapplication.databinding.FragmentPermissionBinding
import com.example.myapplication.view.storecloth.clothdetail.ClothActivity
import com.example.myapplication.view.storecloth.clothdetail.pickupdetail.PickupDetailFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.OrderViewModel
import com.example.myapplication.viewmodel.StoreViewModel
import com.example.myapplication.widget.config.EventObserver
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OrderBottomSheetFragment() :
    BaseBottomSheetFragment<FragmentOrderBinding>(R.layout.fragment_order),
    OrderBottomSheetAdapter.OrderClickListener {
    private lateinit var storeViewModel: StoreViewModel
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var dressViewModel: DressViewModel

    // 색상, 개수 선택 유무
    private var checkedTwoButton = arrayListOf(false, false)

    private lateinit var selectedcolor: String
    private var selectedcolornum: Int = 0

    private lateinit var selectedsize: String
    private var selectedsizenum: Int = 0

    private var orderdatalist = ArrayList<ClothOrderData>()

    private var optiondata: ClothOptionData? = null

    private lateinit var spinnerColor: Spinner
    private lateinit var spinnerSize: Spinner
    // 버튼 활성화 유무
    private var buttonOnOff = false
    // 스피너 목록을 만들 어댑터(색상, 사이즈) 리스트
    private var optionArrayList = ArrayList<ArrayAdapter<String>>()

    // 스피너 목록에서 제일 첫번째 값
    private var spinnercolorlist = arrayListOf("색상")
    private var spinnersizelist = arrayListOf("사이즈")
    private var selectedPrice: Int = 0

    override fun init() {
        storeViewModel = (activity as ClothActivity).storeViewModel
        dressViewModel = (activity as ClothActivity).dressViewModel
        orderViewModel = ViewModelProvider(requireParentFragment()).get(OrderViewModel::class.java)
        binding.ordervm = orderViewModel

        optiondata = orderViewModel.option_data.value
        selectedPrice = optiondata?.clothPrice!!

        //spinnerColor
        spinnerColor = binding.orderSpinnerColor
        spinnerSize = binding.orderSpinnerSize

        // 스피너 색상 목록 불러오기
        if (optiondata != null) {
            for (i in optiondata?.dress_option1?.dress_option_detail_list!!) {
                spinnercolorlist.add(i.dress_option_detail_name)
            }
            for (i in optiondata?.dress_option2?.dress_option_detail_list!!) {
                spinnersizelist.add(i.dress_option_detail_name)
            }
        }

        val orderBottomSheetAdapter = OrderBottomSheetAdapter(this)
        binding.orderRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderBottomSheetAdapter
            addItemDecoration(OrderListDivider(0f, 0f, 10f, 10f, Color.TRANSPARENT))
        }

        // order_data : 선택한 상품 목록
        orderViewModel.order_data.observe(viewLifecycleOwner, Observer<ArrayList<ClothOrderData>> {
            if (it != null) { // 옵션 선택을 했다는 것이므로
                orderBottomSheetAdapter.submitList(it.toMutableList())
                spinnerColor.setSelection(0)  // 색상 스피너 목록 초기화
                spinnerSize.setSelection(0) // 사이즈 스피너 목록 초기화
                checkedTwoButton[0] = false // 색상 체크 기록 초기화
                checkedTwoButton[1] = false // 사이즈 체크 기록 초기화
                if (it.size > 0) { // 선택한 상품이 있다면 버튼 활성화
                    binding.orderTextviewPickupbutton.setBackgroundResource(R.drawable.green_button_background)
                    binding.orderTextviewPickupbutton.setTextColor(Color.WHITE)
                    buttonOnOff = true
                } else { // 선택한 상품이 없다면 버튼 비활성화
                    binding.orderTextviewPickupbutton.setBackgroundResource(R.drawable.gray_button_background)
                    binding.orderTextviewPickupbutton.setTextColor(Color.parseColor("#A4A4A4"))
                    buttonOnOff = false
                }
            } else {
                Log.d("whatisthis", "order_data, 데이터 없음")
            }
        })

        orderbtClickEvent()

        spinnerAdapterSetting()
        spinnerColor.adapter = optionArrayList[0]
        spinnerSize.adapter = optionArrayList[1]

        spinnerColor.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view2: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) { // 목록 중 '색상'을 클릭 했을 경우
                    checkedTwoButton[0] = false
                    selectedcolor = ""
                } else {
                    checkedTwoButton[0] = true
                    selectedcolor = spinnercolorlist[position]
                    selectedcolornum = position
                    if (checkedTwoButton[0] == true && checkedTwoButton[1] == true) {
                        orderdatalist.add(
                            ClothOrderData(
                                selectedcolor,
                                selectedsize,
                                1,
                                selectedPrice,
                                selectedcolornum,
                                selectedsizenum
                            )
                        )
                        orderViewModel.set_order_data(orderdatalist)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                checkedTwoButton[0] = false
            }
        })

        spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected( // 목록 중 '사이즈'를 클릭 했을 경우
                parent: AdapterView<*>?,
                view2: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    checkedTwoButton[1] = false
                    selectedsize = ""
                } else {
                    checkedTwoButton[1] = true
                    selectedsize = spinnersizelist[position]
                    selectedsizenum = position
                    if (checkedTwoButton[0] == true && checkedTwoButton[1] == true) {
                        orderdatalist.add(
                            ClothOrderData(
                                selectedcolor,
                                selectedsize,
                                1,
                                selectedPrice,
                                selectedcolornum,
                                selectedsizenum
                            )
                        )
                        orderViewModel.set_order_data(orderdatalist)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                checkedTwoButton[1] = false
            }
        }
    }

    // 주문 버튼 눌렀을 때 클릭 이벤트
    private fun orderbtClickEvent(){
        orderViewModel.pickup_bt_event.observe(this@OrderBottomSheetFragment, EventObserver {
            if (buttonOnOff) {
                orderViewModel.get_calculate_order_price()
                storeViewModel.get_store_detail_data(dressViewModel.dress_detail_data.value!!.data!!.store_id, "전체")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.clothblank_layout, PickupDetailFragment(), "pickupdetail")
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
                dismiss()
            }

        })
    }

    // 스피너 목록을 보여줄 어댑터 초기화
    private fun spinnerAdapterSetting() {
        val adapterColor =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnercolorlist)
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        optionArrayList.add(adapterColor)

        val adapterSize =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnersizelist)
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        optionArrayList.add(adapterSize)
    }

    override fun onItemPlusClick(_clothorderdata: ClothOrderData, position: Int) {
        _clothorderdata.count++
        orderViewModel.set_order_data(orderdatalist)
    }

    override fun onItemMinusClick(_clothorderdata: ClothOrderData, position: Int) {
        _clothorderdata.count--
        if (_clothorderdata.count <= 0) {
            orderdatalist.remove(_clothorderdata)
            orderViewModel.set_order_data(orderdatalist)
        }
    }

    override fun onItemCloseClick(_clothorderdata: ClothOrderData, position: Int) {
        orderdatalist.remove(_clothorderdata)
        orderViewModel.set_order_data(orderdatalist)
    }

}