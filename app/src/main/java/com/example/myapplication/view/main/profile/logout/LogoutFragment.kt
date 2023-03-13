package com.example.myapplication.view.main.profile.logout

import android.content.Intent
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheetFragment
import com.example.myapplication.databinding.FragmentLogoutBinding
import com.example.myapplication.view.login.MainActivity
import com.kakao.sdk.user.UserApiClient

class LogoutFragment : BaseBottomSheetFragment<FragmentLogoutBinding>(R.layout.fragment_logout) {

    override fun init() {
        binding.logoutButtonYes.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "로그아웃 실패", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
                    // 로그인 화면으로 넘어갈건가??
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    // 로그인 토큰도 지워야하나??
                }
            }
        }
        binding.logoutButtonNo.setOnClickListener {
            dismiss()
        }
    }
}
