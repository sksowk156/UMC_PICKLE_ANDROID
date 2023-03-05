package com.example.myapplication.view.storecloth.clothdetail.ordercomplete

import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderCompleteBinding
import com.example.myapplication.base.BaseFragment

class OrderCompleteFragment : BaseFragment<FragmentOrderCompleteBinding>(R.layout.fragment_order_complete) {
    override fun init() {
        // 앱바 지우기

        initHomeButton()
    }

    private fun initHomeButton(){
        with(binding){
            binding.goHomeButton.setOnClickListener{
//                val nextIntent = Intent(getActivity(), SecondActivity::class.java)
//                startActivity(nextIntent)
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .remove(requireActivity().supportFragmentManager.findFragmentByTag("clothblank")!!)
//                    .commit()

                requireActivity().finish()
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.clothblank_layout, HomeBaseFragment())
//                    .addToBackStack(null)
//                    .commitAllowingStateLoss()
            }
        }
    }
}