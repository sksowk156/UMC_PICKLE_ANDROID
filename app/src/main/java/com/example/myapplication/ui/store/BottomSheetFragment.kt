package com.example.myapplication.ui.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myapplication.R
import com.example.myapplication.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(context: Context) : BottomSheetDialogFragment() {
    private lateinit var viewBinding: BottomSheetBinding
    private val mContext: Context = context

    private lateinit var storeName: String
    private lateinit var clothName: String
    private var clothPrice: Int = 0
    private var multiPrice: Int = 0

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

        //spinnerColor
        val spinnerColor: Spinner = view.findViewById(R.id.spinner_color)
        val colors = resources.getStringArray(R.array.spinner_color)
        val adapterColor = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colors)
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColor.adapter = adapterColor
        val tvColor: TextView = view.findViewById(R.id.tv_color)

        spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tvColor.text = colors[position]
                color = colors[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //spinnerSize
        val spinnerSize: Spinner = view.findViewById(R.id.spinner_size)
        val sizes = resources.getStringArray(R.array.spinner_size)
        val adapterSize = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sizes)
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSize.adapter = adapterSize
        val tvSize: TextView = view.findViewById(R.id.tv_size)

        spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tvSize.text = " / " + sizes[position]
                size = sizes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val btnMinus: TextView = view.findViewById(R.id.btn_minus)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
        val tvMultiPrice: TextView = view.findViewById(R.id.tv_multi_price)
        btnMinus.setOnClickListener{
            if(count > 0)
                count--
            tvCount.text = count.toString()

            multiPrice = clothPrice * count
            Log.d("clothPrice", clothPrice.toString())
            Log.d("multiprice", multiPrice.toString())
            tvMultiPrice.text = multiPrice.toString() + "원"
        }

        val btnPlus: TextView = view.findViewById(R.id.btn_plus)
        btnPlus.setOnClickListener{
            count++
            tvCount.text = count.toString()

            multiPrice = clothPrice * count
            Log.d("clothPrice", clothPrice.toString())
            Log.d("totalprice", multiPrice.toString())
            tvMultiPrice.text = multiPrice.toString() + "원"
        }

        val btnPickup : Button = view.findViewById(R.id.btn_pickup)
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

        return view
    }

}