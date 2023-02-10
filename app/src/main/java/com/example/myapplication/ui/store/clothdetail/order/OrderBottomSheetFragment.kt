package com.example.myapplication.ui.store.clothdetail.order

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderBinding
import com.example.myapplication.ui.main.profile.orderstatus.OrderListDivider
import com.example.myapplication.ui.store.clothdetail.ClothOrderData
import com.example.myapplication.ui.store.clothdetail.pickupdetail.PickupDetailFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.OrderViewModel
import com.example.myapplication.viewmodel.StoreViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OrderBottomSheetFragment() :
    BottomSheetDialogFragment(R.layout.fragment_order), OrderBottomSheetAdapter.OrderClickListener{
    private lateinit var binding: FragmentOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var dressViewModel: DressViewModel

    private var checkedTwoButton = ArrayList<Boolean>()

    private var selectedPrice: Int = 0
    private lateinit var selectedcolor: String
    private lateinit var selectedsize: String

    private var orderdatalist = ArrayList<ClothOrderData>()

    private lateinit var spinnerColor: Spinner
    private lateinit var spinnerSize: Spinner

    private lateinit var spinnercolorlist: Array<String>
    private lateinit var spinnersizelist: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        orderViewModel = ViewModelProvider(requireParentFragment()).get(OrderViewModel::class.java)
        dressViewModel = ViewModelProvider(requireActivity()).get(DressViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 스피너 버튼 클릭 여부 확인 변수
        checkedTwoButton.add(false)
        checkedTwoButton.add(false)

        // 메뉴 선택 결과
        val orderBottomSheetAdapter = OrderBottomSheetAdapter(this)
        binding.orderRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderBottomSheetAdapter
            addItemDecoration(OrderListDivider(0f,0f,10f,10f, Color.TRANSPARENT))
        }
        orderViewModel.order_data.observe(viewLifecycleOwner, Observer<ArrayList<ClothOrderData>> {
            if (it != null) {
                orderBottomSheetAdapter.submitList(it.toMutableList())
                spinnerColor.setSelection(0)  // 스피너 목록 초기화
                spinnerSize.setSelection(0) // 스피너 목록 촉화
                checkedTwoButton[0] = false
                checkedTwoButton[1] = false
                if(it.size > 0){ // 선택 목록이 있다면 버튼 활성화
                    binding.orderTextviewPickupbutton.setBackgroundResource(R.drawable.green_button_background)
                    binding.orderTextviewPickupbutton.setTextColor(Color.WHITE)
                }else{ // 선택 목록이 없다면 버튼 비활성화
                    binding.orderTextviewPickupbutton.setBackgroundResource(R.drawable.gray_button_background)
                    binding.orderTextviewPickupbutton.setTextColor(Color.parseColor("#A4A4A4"))
                }
            } else {
                Log.d("whatisthis", "11네트워크 오류가 발생했습니다.")
            }
        })

        //spinnerColor
        spinnerColor = binding.orderSpinnerColor
        spinnercolorlist = resources.getStringArray(R.array.spinner_color)
        val adapterColor =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnercolorlist)
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColor.adapter = adapterColor
        spinnerColor.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view2: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    checkedTwoButton[0] = false
                    selectedcolor = ""
                } else {
                    checkedTwoButton[0] = true
                    selectedcolor = spinnercolorlist[position]
                    if (checkedTwoButton[0] == true && checkedTwoButton[1] == true) {
                        orderdatalist.add(
                            ClothOrderData(
                                selectedcolor,
                                selectedsize,
                                1,
                                selectedPrice
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

        //spinnerSize
        spinnerSize = binding.orderSpinnerSize
        spinnersizelist = resources.getStringArray(R.array.spinner_size)
        val adapterSize =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnersizelist)
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSize.adapter = adapterSize

        spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
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
                    if (checkedTwoButton[0] == true && checkedTwoButton[1] == true) {
                        orderdatalist.add(
                            ClothOrderData(
                                selectedcolor,
                                selectedsize,
                                1,
                                selectedPrice
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

        binding.orderTextviewPickupbutton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.clothblank_layout, PickupDetailFragment(), "pickupdetail")
                .addToBackStack(null)
                .commitAllowingStateLoss()
            dismiss()
        }
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