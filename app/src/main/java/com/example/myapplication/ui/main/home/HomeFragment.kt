package com.example.myapplication.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.ui.main.BaseFragment
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun init() {
        initAppbar()
        initSlide()
        rcView()
    }

    private fun initAppbar() {
        val toolbar: androidx.appcompat.widget.Toolbar
        binding.apply {
            toolbar = homeToolbar.toolbar
            // 툴바 이름정하기
            toolbar.setTitle("홈")
//            // 툴바 뒤로가기 버튼 아이콘 정하기
//            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
//            // 툴바 뒤로가기 버튼 클릭 이벤트
//            toolbar.setNavigationOnClickListener {  }
            // 툴바 메뉴 넣기
            toolbar.inflateMenu(R.menu.menu_appbar)
            // 툴바 클릭 이벤트
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.search -> {
                    }
                    R.id.notification -> {
                    }
                }
                true
            }
        }
    }

    private fun initSlide(){

        lateinit var imageUrl: ArrayList<String>
        // on below line we are creating a variable
        // for our array list for storing our images.
        imageUrl = ArrayList()

        // on below line we are adding data to our image url array list.
        imageUrl =
            (imageUrl + "https://images.unsplash.com/photo-1549298916-b41d501d3772?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c2hvZXN8ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://images.unsplash.com/photo-1662532577856-e8ee8b138a8b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8NXw5NzY3MDkyfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=600&q=60") as ArrayList<String>
        imageUrl =
            (imageUrl + "https://images.unsplash.com/photo-1566150905458-1bf1fc113f0d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8YmFnfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60") as ArrayList<String>


        // on below line we are creating
        // a variable for our slider view.
        lateinit var sliderView: SliderView


        sliderView=binding.slider

        // on below line we are creating
        // a variable for our slider adapter.
        lateinit var sliderAdapter: SliderAdapter

        sliderAdapter=SliderAdapter(imageUrl)

        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // on below line we are setting adapter for our slider.
        sliderView.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        sliderView.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        sliderView.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        sliderView.startAutoCycle()
    }



    private val dataSet: ArrayList<List<String>> = arrayListOf<List<String>>()



    private fun rcView(){
        addData()






       /* binding.apply {
            recyclerView.apply{
               LinearLayoutManager(this@HomeFragment.context,LinearLayoutManager.VERTICAL,false)
                RecyclerViewAdapter(dataSet)
                addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            }
        }*/
        binding.apply{
            recyclerView.layoutManager=LinearLayoutManager(this@HomeFragment.context,LinearLayoutManager.VERTICAL,false)
            recyclerView.adapter=RecyclerViewAdapter(dataSet)
            recyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        }

    }

    private fun addData(){
        for(i in 0 .. 9){
            dataSet.add(listOf("$i th main","$i th sub"))
        }
    }
}