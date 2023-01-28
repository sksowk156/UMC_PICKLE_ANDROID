package com.example.myapplication.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRecentBinding
import com.example.myapplication.ui.SecondActivity
import com.example.myapplication.ui.main.BaseFragment

class RecentFragment : BaseFragment<FragmentRecentBinding>(R.layout.fragment_recent) {
    // 뒤로가기 버튼을 눌렀을 때를 위한 callback 변수
    private lateinit var callback: OnBackPressedCallback
    lateinit var menuProvider_recent : MenuProvider
    lateinit var menuHost : MenuHost

    override fun init() {
        rcView()
        initBackbtn()

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


        binding.newRecyclerView.apply {

            layoutManager= GridLayoutManager(this.context,2)
            adapter=CardViewAdapter(clothesList)

        }




    }


    private fun initBackbtn(){
        // 뒤로 가기 버튼을 눌렀을 때 이벤트 처리
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager
                    .popBackStackImmediate(null, 0)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }



    // 프래그먼트가 종료되면 callback 변수 제거
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}