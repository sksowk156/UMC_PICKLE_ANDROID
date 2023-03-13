package com.example.myapplication.view.main.profile.withdrawal

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheetFragment
import com.example.myapplication.databinding.FragmentWithdrawalBinding
import com.example.myapplication.view.login.MainActivity
import com.example.myapplication.viewmodel.LoginViewModel
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WithdrawalFragment :
    BaseBottomSheetFragment<FragmentWithdrawalBinding>(R.layout.fragment_withdrawal) {
    val loginViewModel: LoginViewModel by activityViewModels<LoginViewModel>()

    override fun init() {
        Log.d("whatisthis",loginViewModel.toString())
        binding.withdrawalButtonYes.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "회원탈퇴 실패", Toast.LENGTH_SHORT).show()
                } else {
                    loginViewModel.deleteJwt()
                    Toast.makeText(requireContext(), "회원탈퇴 성공", Toast.LENGTH_SHORT).show()
                    // 로그인 화면으로 넘어갈건가??
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    // 로그인 토큰도 지워야하나??
                }
            }
        }

        binding.withdrawalButtonNo.setOnClickListener {
            dismiss()
        }
    }

}