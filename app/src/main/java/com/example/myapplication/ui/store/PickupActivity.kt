package com.example.myapplication.ui.store

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityPickupBinding
import java.util.*

class PickupActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityPickupBinding

    private lateinit var storeName: String
    private lateinit var clothName: String
    private var multiPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPickupBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val extras = intent.extras
        val color = extras!!["color"] as String
        val size = extras!!["size"] as String
        val count = extras!!["count"] as Int
        val storeName = extras!!["storeName"] as String
        val clothName = extras!!["clothName"] as String
        val multiPrice = extras!!["multiPrice"] as Int

        viewBinding.tvStoreName.text = storeName
        viewBinding.tvClothName.text = clothName
        viewBinding.tvClothInfo.text = color + " / " + size + " / " + count.toString() +"개"
        viewBinding.tvMultiPrice.text = multiPrice.toString() + "원"

        viewBinding.tvDateDialog.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                viewBinding.tvDateDialog.text = "${year}. ${month+1}. ${day}"

            }
            DatePickerDialog(this, data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        viewBinding.tvTotalPrice.text = multiPrice.toString() + "원"
    }
}