package com.example.myapplication.view.storecloth.clothdetail.order

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheetFragment
import com.example.myapplication.data.remote.model.order.ClothOptionData
import com.example.myapplication.data.remote.model.order.ClothOrderData
import com.example.myapplication.databinding.FragmentOrderBinding
import com.example.myapplication.view.main.profile.inquiry.ToggleAnimation
import com.example.myapplication.view.main.profile.orderstatus.OrderListDivider
import com.example.myapplication.view.storecloth.clothdetail.pickupdetail.PickupDetailFragment
import com.example.myapplication.viewmodel.DressViewModel
import com.example.myapplication.viewmodel.OrderViewModel
import com.example.myapplication.viewmodel.StoreViewModel
import com.example.myapplication.widget.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderBottomSheetFragment() :
    BaseBottomSheetFragment<FragmentOrderBinding>(R.layout.fragment_order),
    OrderBottomSheetListAdapter.OrderClickListener {
    val storeViewModel: StoreViewModel by activityViewModels<StoreViewModel>()
    val dressViewModel: DressViewModel by activityViewModels<DressViewModel>()
    private lateinit var orderViewModel: OrderViewModel

    // 색상, 개수 선택 유무
    private var checkedTwoButton = arrayListOf(false, false)

    private lateinit var selectedcolor: String
    private var selectedcolornum: Int = 0

    private lateinit var selectedsize: String
    private var selectedsizenum: Int = 0

    private var orderdatalist = ArrayList<ClothOrderData>()

    private var optiondata: ClothOptionData? = null

    // 버튼 활성화 유무
    private var buttonOnOff = false

    private var selectedPrice: Int = 0

    private lateinit var colorAdapter: OrderBottomSheetOptionAdapter
    private lateinit var sizeAdapter: OrderBottomSheetOptionAdapter

    private lateinit var colorOptionLayout: LinearLayout
    private lateinit var sizeOptionLayout: LinearLayout

    override fun init() {
        orderViewModel = ViewModelProvider(requireParentFragment()).get(OrderViewModel::class.java)
        binding.ordervm = orderViewModel

        optiondata = orderViewModel.option_data.value
        selectedPrice = optiondata?.clothPrice!!

        spinnerAdapterSetting()

        colorOptionLayout = binding.orderInnerlayoutColor2
        binding.orderInnerlayout1.setOnClickListener {
            binding.orderTextviewColor.text = "색상"
            binding.orderTextviewColor.setTextColor(Color.parseColor("#A4A4A4"))
            if(checkedTwoButton[0]==true){
                checkedTwoButton[0] = false
            }

            colorOptionLayout.isVisible = toggleLayout(
                it.findViewById(binding.orderImageviewArrow1.id),
                colorOptionLayout
            )
            if(sizeOptionLayout.isVisible){
                sizeOptionLayout.isVisible = toggleLayout(
                    binding.orderInnerlayout2.findViewById(binding.orderImageviewArrow2.id),
                    sizeOptionLayout
                )
            }
        }

        sizeOptionLayout = binding.orderInnerlayoutSize2
        binding.orderInnerlayout2.setOnClickListener {
            binding.orderTextviewSize.text = "사이즈"
            binding.orderTextviewSize.setTextColor(Color.parseColor("#A4A4A4"))
            if(checkedTwoButton[1]==true){
                checkedTwoButton[1] = false
            }

            if(colorOptionLayout.isVisible){
                colorOptionLayout.isVisible = toggleLayout(
                    binding.orderInnerlayout1.findViewById(binding.orderImageviewArrow1.id),
                    colorOptionLayout
                )
            }
            sizeOptionLayout.isVisible = toggleLayout(
                it.findViewById(binding.orderImageviewArrow2.id),
                sizeOptionLayout
            )

        }

        val orderBottomSheetAdapter = OrderBottomSheetListAdapter(this)
        binding.orderRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderBottomSheetAdapter
            addItemDecoration(OrderListDivider(0f, 0f, 10f, 10f, Color.TRANSPARENT))
        }

        // order_data : 선택한 상품 목록
        orderViewModel.order_data.observe(viewLifecycleOwner, Observer<ArrayList<ClothOrderData>> {
            if (it != null) { // 옵션 선택을 했다는 것이므로
                orderBottomSheetAdapter.submitList(it.toMutableList())

                if(checkedTwoButton[0] && checkedTwoButton[1]){
                    binding.orderTextviewColor.text = "색상"
                    binding.orderTextviewSize.text = "사이즈"

                    binding.orderTextviewColor.setTextColor(Color.parseColor("#A4A4A4"))
                    binding.orderTextviewSize.setTextColor(Color.parseColor("#A4A4A4"))

                    checkedTwoButton[0] = false // 색상 체크 기록 초기화
                    checkedTwoButton[1] = false // 사이즈 체크 기록 초기화
                }

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
    }

    // 주문 버튼 눌렀을 때 클릭 이벤트
    private fun orderbtClickEvent() {
        orderViewModel.pickup_bt_event.observe(this@OrderBottomSheetFragment, EventObserver {
            if (buttonOnOff) {
                orderViewModel.get_calculate_order_price()
                storeViewModel.get_store_detail_data(
                    dressViewModel.dress_detail_data.value!!.data!!.store_id,
                    "전체"
                )
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
        colorAdapter = OrderBottomSheetOptionAdapter(object :
            OrderBottomSheetOptionAdapter.OptionClickListener {
            override fun onItemBackgroundClick(view: View, position: Int,id : Int) {
                checkedTwoButton[0] = true
                selectedcolor = view.findViewById<TextView>(R.id.orderbottomsheetoption_textview_option).text.toString()
                selectedcolornum = id

                binding.orderTextviewColor.text = selectedcolor
                binding.orderTextviewColor.setTextColor(Color.parseColor("#1E1E1E"))

                colorOptionLayout.isVisible = toggleLayout(
                    binding.orderInnerlayout1.findViewById(binding.orderImageviewArrow1.id),
                    colorOptionLayout
                )

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
        })

        binding.orderRecyclerviewColor.adapter = colorAdapter
        binding.orderRecyclerviewColor.layoutManager = LinearLayoutManager(requireContext())
        colorAdapter.userList =
            optiondata?.dress_option1?.dress_option_detail_list!!.toMutableList()
        binding.orderRecyclerviewColor.addItemDecoration(
            OrderListDivider(
                0f,
                0f,
                4f,
                0f,
                Color.parseColor("#E1E1E1")
            )
        )

        colorAdapter.notifyDataSetChanged()

        sizeAdapter = OrderBottomSheetOptionAdapter(object :
            OrderBottomSheetOptionAdapter.OptionClickListener {
            override fun onItemBackgroundClick(view: View, position: Int,id : Int) {
                checkedTwoButton[1] = true
                selectedsize =
                    view.findViewById<TextView>(R.id.orderbottomsheetoption_textview_option).text.toString()
                selectedsizenum = id

                binding.orderTextviewSize.text = selectedsize
                binding.orderTextviewSize.setTextColor(Color.parseColor("#1E1E1E"))

                sizeOptionLayout.isVisible = toggleLayout(
                    binding.orderInnerlayout2.findViewById(binding.orderImageviewArrow2.id),
                    sizeOptionLayout
                )

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
        })

        binding.orderRecyclerviewSize.adapter = sizeAdapter
        binding.orderRecyclerviewSize.layoutManager = LinearLayoutManager(requireContext())
        sizeAdapter.userList = optiondata?.dress_option2?.dress_option_detail_list!!.toMutableList()
        binding.orderRecyclerviewSize.addItemDecoration(
            OrderListDivider(
                0f,
                0f,
                4f,
                0f,
                Color.parseColor("#E1E1E1")
            )
        )

        sizeAdapter.notifyDataSetChanged()

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

    private fun toggleLayout(
        view: View,
        layoutExpand: LinearLayout
    ): Boolean {
        // 2
        ToggleAnimation.toggleArrow(view, layoutExpand.isVisible)
        if (!layoutExpand.isVisible) {
            ToggleAnimation.expand(layoutExpand)
        } else {
            ToggleAnimation.collapse(layoutExpand)
        }
        return layoutExpand.isVisible
    }

}