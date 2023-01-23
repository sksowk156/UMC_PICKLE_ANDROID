package com.example.myapplication.ui.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = BottomSheetBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)

        //spinnerColor
        val spinnerColor: Spinner = view.findViewById(R.id.spinner_color)
        val color = resources.getStringArray(R.array.spinner_color)
        val adapterColor = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, color)
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColor.adapter = adapterColor

        spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // if(position != 0) Toast.makeText(this@MainActivity, itemList[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //spinnerSize
        val spinnerSize: Spinner = view.findViewById(R.id.spinner_size)
        val size = resources.getStringArray(R.array.spinner_size)
        val adapterSize = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, size)
        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSize.adapter = adapterSize

        spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // if(position != 0) Toast.makeText(this@MainActivity, itemList[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val btnPickup : Button = view.findViewById(R.id.btn_pickup)
        btnPickup.setOnClickListener {
            val intent = Intent(getActivity(), PickupActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}