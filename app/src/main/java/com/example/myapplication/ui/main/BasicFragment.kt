package com.example.myapplication.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseFragment<T: ViewDataBinding>(
    @LayoutRes val layoutResId: Int
) : Fragment(){
    private var _binding: T? = null
    protected val binding get() = _binding!!

    // 뒤로가기 버튼을 눌렀을 때를 위한 callback 변수
    private lateinit var callback: OnBackPressedCallback

    // 뒤로가기 버튼을 눌렀을 때
    protected fun backpress(){
        // 최상위 Fragment가 아닐 때만 뒤로가기 버튼 활성화
        // -> 최상위 Fragment는 bottomnavigationview에 올라가는 것이기 때문에 뒤로가기 버튼을 활성화 하면
        // bottomnavigationview의 메뉴 선택 기록이 popbackstack 하게 된다.( 단, 메뉴 선택 기록을 기록했을 경우 )
        if(parentFragmentManager.backStackEntryCount != 0){
            // 뒤로 가기 버튼을 눌렀을 때 이벤트 처리
            callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager
                        .popBackStackImmediate(null, 0)
                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        init()
        backpress()
    }

    protected abstract fun init()

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // 하단 바 숨길때
    protected fun hideBottomNavigation(bool : Boolean){
        val bottom : BottomNavigationView = requireActivity().findViewById(R.id.second_bottomNavigationView)
        if(bool == true){
            bottom.visibility = View.GONE
        }else{
            bottom.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 프래그먼트가 종료되면 callback 변수 제거
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}