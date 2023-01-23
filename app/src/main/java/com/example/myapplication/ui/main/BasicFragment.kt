package com.example.myapplication.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.R
import com.example.myapplication.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseFragment<T: ViewDataBinding>(
    @LayoutRes val layoutResId: Int
) : Fragment(){
    private var _binding: T? = null
    protected val binding get() = _binding!!

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
    }

    protected abstract fun init()

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // 하단 바 숨길때
    protected fun hideBottomNavigation(bool : Boolean){
        val bottom : BottomNavigationView = requireActivity().findViewById(R.id.main_bottomNavigationView)
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
}