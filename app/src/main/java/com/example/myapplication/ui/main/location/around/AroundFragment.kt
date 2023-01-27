package com.example.myapplication.ui.main.location.around

import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAroundBinding
import com.example.myapplication.ui.main.BaseFragment
import com.example.myapplication.ui.main.location.MapAroundData


class AroundFragment : BaseFragment<FragmentAroundBinding>(R.layout.fragment_around) {

    override fun init() {
        // 플로팅 버튼 이벤트 처리
        binding.aroundFab.setOnClickListener {
            parentFragmentManager
                .popBackStackImmediate(null, 0)
        }
        initBackbtn()
        initRecyclerView()
    }

    private fun initBackbtn(){
//        // 뒤로 가기 버튼을 눌렀을 때 이벤트 처리
//        callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                parentFragmentManager
//                    .popBackStackImmediate(null, 0)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    private fun initRecyclerView(){
        with(binding) {
            // 1. 어댑터 생성 및 리사이클러뷰 연결
            val aroundAdapter = AroundAdapter()

            val marketAroundDataTemp: ArrayList<MapAroundData> = ArrayList()
            marketAroundDataTemp.add(MapAroundData("1", "title1", "subtitle1"))
            marketAroundDataTemp.add(MapAroundData("2", "title2", "subtitle2"))
            marketAroundDataTemp.add(MapAroundData("3", "title3", "subtitle3"))
            marketAroundDataTemp.add(MapAroundData("4", "title4", "subtitle4"))
            marketAroundDataTemp.add(MapAroundData("5", "title5", "subtitle5"))

            aroundRecyclerview.adapter = aroundAdapter
            aroundRecyclerview.layoutManager = LinearLayoutManager(context)
            aroundAdapter.userList = marketAroundDataTemp
            aroundAdapter.notifyDataSetChanged()
        }
    }

//    // 프래그먼트가 종료되면 callback 변수 제거
//    override fun onDetach() {
//        super.onDetach()
//        callback.remove()
//    }
}