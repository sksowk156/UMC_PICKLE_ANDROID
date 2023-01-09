package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.main.chat.ChatFragment
import com.example.myapplication.main.favorite.FavoriteFragment
import com.example.myapplication.main.home.HomeFragment
import com.example.myapplication.main.home.SliderAdapter
import com.example.myapplication.main.location.LocationFragment
import com.example.myapplication.main.profile.ProfileFragment
import com.smarteist.autoimageslider.SliderView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var imageUrl: ArrayList<String>

    // on below line we are creating
    // a variable for our slider view.
    lateinit var sliderView: SliderView

    // on below line we are creating
    // a variable for our slider adapter.
    lateinit var sliderAdapter: SliderAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())


        //fragment(5)
        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.menu_home->replaceFragment(HomeFragment())
                R.id.menu_favorite->replaceFragment(FavoriteFragment())
                R.id.menu_map->replaceFragment(LocationFragment())
                R.id.menu_chat->replaceFragment(ChatFragment())
                R.id.menu_profile->replaceFragment(ProfileFragment())

                else->{

                }
            }
            true
        }

    }


    private fun replaceFragment(fragment: Fragment){

        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }


}