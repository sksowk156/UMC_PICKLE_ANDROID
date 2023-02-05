package com.example.myapplication.ui.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.BottomSheetBinding
import com.example.myapplication.ui.main.search.SearchhistoryAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(context: Context) : BottomSheetDialogFragment(R.layout.bottom_sheet) {
    private lateinit var viewBinding: BottomSheetBinding
    private val mContext: Context = context
    private var isColorFirstSelected : Boolean = true
    private var isSizeFirstSelected : Boolean = true
    private var searchFilter : Int = -1

    private lateinit var storeName: String
    private lateinit var clothName: String
    private var clothPrice: Int = 0
    private var multiPrice: Int = 0

    private var clothCountList = mutableListOf<View>()
    private var clothCountIndex : Int = 0

    private lateinit var color: String
    private lateinit var size: String
    private var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = BottomSheetBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)

        storeName = arguments?.getString("storeName").toString()
        clothName = arguments?.getString("clothName").toString()
        clothPrice = arguments?.getInt("clothPrice")!!
        //  var clothPriceString = arguments?.getInt("clothPrice").toString()
        //  clothPrice = clothPriceString.toInt()

        Log.d("bottomPrice", clothPrice.toString())

        val dataList: ArrayList<ClothCount> = arrayListOf()
        val clothCountRVadapter = ClothCountRVAdapter(dataList, mContext)
        var rvClothCount: View = view.findViewById(R.id.rv_cloth_count)

        viewBinding.rvClothCount.adapter = clothCountRVadapter
        viewBinding.rvClothCount.layoutManager = LinearLayoutManager(mContext)


        //spinnerColor
        val spinnerColor: Spinner = viewBinding.spinnerColor
        val colors = resources.getStringArray(R.array.spinner_color)
        val adapterColor =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colors)
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColor.adapter = adapterColor
        spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view2: View?,
                position: Int,
                id: Long
            ) {
                spinnerColor.setSelection(0, false)
                if(isColorFirstSelected){
                    isColorFirstSelected = false
                }
                else if(searchFilter == 1) {
                    color = colors[position]
                    dataList.apply {
                        add(ClothCount(color, " ", 1, clothPrice))
                    }
                    Log.d("dataList", dataList.last().color)
                    clothCountRVadapter.notifyDataSetChanged()
                    searchFilter = -1
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerColor.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(view: View, event: MotionEvent): Boolean{
                searchFilter = 1
                return false
            }
        })



        //spinnerSize
        val spinnerSize: Spinner = viewBinding.spinnerSize
        val sizes = resources.getStringArray(R.array.spinner_size)
        val adapterSize = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sizes)
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSize.adapter = adapterSize
//        val tvSize: TextView = view.findViewById(R.id.tv_size)

        spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view2: View?,
                position: Int,
                id: Long
            ) {
                if(isSizeFirstSelected){
                    isSizeFirstSelected = false
                }
                else {
                    spinnerSize.setSelection(0, false)
                    if(dataList.size == 0) {
                        Toast.makeText(mContext, "상위 옵션 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else if(searchFilter == 2){
                        size = sizes[position]
                        dataList.last().size = size
                        clothCountRVadapter.notifyDataSetChanged()
                        viewBinding.ivPickup.setImageResource(R.drawable.button_pickup_abled)
                        searchFilter = -1
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerSize.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(view: View, event: MotionEvent): Boolean{
                searchFilter = 2
                return false
            }
        })

//        clothCountRVadapter.setItemClickListener(object: ClothCountRVAdapter.OnItemClickListener{
//            override fun onPlusMinusClick(v: View, position: Int): Int {
//                return clothPrice
//            }
//        })

        val btnPickup = viewBinding.ivPickup
        btnPickup.setOnClickListener {
            val intent = Intent(getActivity(), PickupActivity::class.java)
            intent.putExtra("color", color)
            intent.putExtra("size", size)
            intent.putExtra("count", count)
            intent.putExtra("storeName", storeName)
            intent.putExtra("clothName", clothName)
            intent.putExtra("multiPrice", multiPrice)
            startActivity(intent)
        }

        return viewBinding.root
    }

}