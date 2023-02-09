package com.example.myapplication.ui.main.home

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.ItemClickInterface
import com.example.myapplication.ui.main.home.newclothe.NewFragment
import com.example.myapplication.ui.main.home.recent.CardViewAdapter
import com.example.myapplication.ui.main.home.recent.RecentFragment
import com.example.myapplication.ui.store.clothdetail.ClothActivity
import com.example.myapplication.ui.store.storedetail.StoreActivity
import com.smarteist.autoimageslider.SliderView

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), ItemClickInterface {

    lateinit var newrecentadapter : HomeCardViewAdapter
    lateinit var recommendadapter : CardViewAdapter
    lateinit var imageList:ArrayList<Int>

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

        binding.button2.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.home_base_layout, NewFragment(),"new")
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }


    private fun initSlide(){
        imageList=ArrayList()
        imageList.add(R.drawable.img_2)
        imageList.add(R.drawable.img_3)
        imageList.add(R.drawable.img_4)
        imageList.add(R.drawable.img_5)
        imageList.add(R.drawable.img_6)
        // on below line we are creating
        // a variable for our slider view.
        lateinit var sliderView: SliderView
        sliderView=binding.slider
        // on below line we are creating
        // a variable for our slider adapter.
        lateinit var sliderAdapter: SliderAdapter
        sliderAdapter=SliderAdapter(imageList)
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
            30000,
            false
        )
        clothesList.add(clothes1)
        newclothesList.add(clothes1)

        val clothes2=Clothes(
            R.drawable.two,
            "store2",
            "옷2",
            30000,
            false
        )
        clothesList.add(clothes2)
        newclothesList.add(clothes1)

        val clothes3=Clothes(
            R.drawable.two,
            "store1",
            "옷3",
            30000,
            false
        )
        clothesList.add(clothes3)
        newclothesList.add(clothes1)

        val clothes4= Clothes(
            R.drawable.one,
            "store1",
            "옷4",
            30000,
            false
        )
        clothesList.add(clothes4)
        newclothesList.add(clothes1)

        val clothes5=Clothes(
            R.drawable.two,
            "store1",
            "옷5",
            30000,
            false
        )
        clothesList.add(clothes5)
        newclothesList.add(clothes1)
    }

    private fun rcView(){
        addClothes()
        newrecentadapter = HomeCardViewAdapter(this@HomeFragment)
        newrecentadapter.submitList(clothesList.toMutableList())

        binding.recyclerView.apply {
            layoutManager=
                LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
            adapter=newrecentadapter
        }

        binding.SecondRecyclerView.apply {
            layoutManager=
                LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
            adapter=newrecentadapter
        }

        recommendadapter = CardViewAdapter(this@HomeFragment)
        binding.homeRecyclerview3.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = recommendadapter
            recommendadapter.submitList(newclothesList.toMutableList())
        }
    }

    override fun onItemImageClick(view: View, position: Int) {
        val intent = Intent(getActivity(), ClothActivity::class.java)
        intent.putExtra("storeName","store1")
        intent.putExtra("clothName","옷1")
        intent.putExtra("clothPrice",30000)

        startActivity(intent)
    }

    override fun onItemMarketNameClick(view: View, position: Int) {
        val intent = Intent(getActivity(), StoreActivity::class.java)
        startActivity(intent)
    }

    override fun onItemButtonClick(view: View, position: Int) {
        showToast("clicked!!")
    }

    override fun onItemFavoriteClick(view: View, position: Int) {
        if(view == view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)){
            if (clothesList[position].like == false) {
                //화면에 보여주기
                Glide.with(this@HomeFragment)
                    .load(R.drawable.icon_favorite_filledpink) //이미지
                    .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
                clothesList[position].like = true
            } else {
                //화면에 보여주기
                Glide.with(this@HomeFragment)
                    .load(R.drawable.icon_favorite_whiteline) //이미지
                    .into(view.findViewById<ImageButton>(R.id.card_imagebutton_favorite)) //보여줄 위치
                clothesList[position].like = false
            }
        }else{
            if (newclothesList[position].like == false) {
                //화면에 보여주기
                Glide.with(this@HomeFragment)
                    .load(R.drawable.icon_favorite_filledpink) //이미지
                    .into(view.findViewById<ImageButton>(R.id.homecard_imagebutton_favorite)) //보여줄 위치
                newclothesList[position].like = true
            } else {
                //화면에 보여주기
                Glide.with(this@HomeFragment)
                    .load(R.drawable.icon_favorite_whiteline) //이미지
                    .into(view.findViewById<ImageButton>(R.id.homecard_imagebutton_favorite)) //보여줄 위치
                newclothesList[position].like = false
            }
        }
    }
}