package com.example.myapplication.ui.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityClothBinding

class ClothActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityClothBinding
    private lateinit var viewpager: ViewPager2

    private lateinit var storeName: String
    private lateinit var clothName: String
    private var clothPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        viewBinding = ActivityClothBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //어댑터 설정
        viewpager = viewBinding.viewPager2
        viewpager.adapter = ViewPagerAdapter(getImageList())
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //indicator 설정
        val indicator = viewBinding.indicator
        indicator.setViewPager(viewpager)

        val extras = intent.extras
        storeName = extras!!["storeName"] as String
        clothName = extras!!["clothName"] as String
        clothPrice = extras!!["clothPrice"] as Int

        viewBinding.tvStoreName2.text = storeName
        viewBinding.tvClothName2.text = clothName
        viewBinding.tvClothPrice2.text = clothPrice.toString()

        val btnOrder = viewBinding.btnOrder
        btnOrder.setOnClickListener {
            val bottomSheet = BottomSheetFragment(applicationContext)
            //bottomSheet.setContentView(R.layout.fragment_bottom_sheet)

            var bundle = Bundle()
            bundle.putString("storeName", storeName)
            bundle.putString("clothName", clothName)
            bundle.putInt("clothPrice", clothPrice)
            bottomSheet.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

//        var intent = Intent()
//        Log.d("get", intent.getStringExtra("storeName").toString())
//        Log.d("check", intent.hasExtra("storeName").toString())
//        viewBinding.tvStoreName2.text = intent.getStringExtra("storeName").toString()
//        viewBinding.tvClothName2.text = intent.getStringExtra("clothName").toString()
//        viewBinding.tvClothPrice2.text = intent.getStringExtra("clothPrice").toString()
    }

    private fun getImageList(): ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.cardigan1,
            R.drawable.cardigan2,
            R.drawable.cardigan3,
            R.drawable.cardigan4
        )
    }
}