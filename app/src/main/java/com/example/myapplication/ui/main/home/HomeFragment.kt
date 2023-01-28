package com.example.myapplication.ui.main.home


import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.R.layout.fragment_home_base
import com.example.myapplication.R.layout.fragment_recent
import com.example.myapplication.databinding.FragmentHomeBaseBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.location.around.AroundFragment
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_base.*
import com.example.myapplication.R.id.home_base_layout
import com.example.myapplication.ui.SecondActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),ClothesClickListener {

    lateinit var menuHost : MenuHost
    lateinit var menuProvider_base : MenuProvider

    override fun init() {
        initSlide()
        rcView()
        binding.button.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .add(R.id.home_base_layout, RecentFragment(),"recent")
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.secondbutton.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.home_base_layout, NewFragment(),"new")
                .addToBackStack(null)
                .commitAllowingStateLoss()
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



    private fun addClothes(){

        val clothes1=Clothes(
            R.drawable.one,
            "store1",
            "옷1",
            30000
        )
        clothesList.add(clothes1)
        newclothesList.add(clothes1)

        val clothes2=Clothes(
            R.drawable.two,
            "store2",
            "옷2",
            30000
        )
        clothesList.add(clothes2)
        newclothesList.add(clothes1)

        val clothes3=Clothes(
            R.drawable.two,
            "store1",
            "옷3",
            30000
        )
        clothesList.add(clothes3)
        newclothesList.add(clothes1)

        val clothes4=Clothes(
            R.drawable.one,
            "store1",
            "옷4",
            30000
        )
        clothesList.add(clothes4)
        newclothesList.add(clothes1)

        val clothes5=Clothes(
            R.drawable.two,
            "store1",
            "옷5",
            30000
        )
        clothesList.add(clothes5)
        newclothesList.add(clothes1)

    }



    private fun rcView(){
        addClothes()


        binding.recyclerView.apply {

            layoutManager=
                LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
            adapter=CardViewAdapter(clothesList)

        }


        binding.SecondRecyclerView.apply {

            layoutManager=
                LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
            adapter=CardViewAdapter(newclothesList)

        }

    }

    override fun onClick(clothes: Clothes) {
        val intent= Intent(this@HomeFragment.context,javaClass)
        intent.putExtra(CLOTHES_ID_EXTRA,clothes.id)
    }

  /*  private fun addData(){
        for(i in 0 .. 9){
            dataSet.add(listOf("$i th main","$i th sub"))
        }
    }*/


}