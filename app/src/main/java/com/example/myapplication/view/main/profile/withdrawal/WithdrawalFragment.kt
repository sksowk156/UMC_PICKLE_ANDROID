package com.example.myapplication.view.main.profile.withdrawal

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.ApplicationClass
import com.example.myapplication.R
import com.example.myapplication.view.login.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kakao.sdk.user.UserApiClient

class WithdrawalFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdrawal, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.AppBottomSheetDialogTheme2)

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var withdrawalyes = view.findViewById<Button>(R.id.withdrawal_button_yes)
        var withdrawalno = view.findViewById<Button>(R.id.withdrawal_button_no)

        withdrawalyes.setOnClickListener {
            UserApiClient.instance.unlink  { error ->
                if(error != null){
                    Toast.makeText(requireContext(),"회원탈퇴 실패", Toast.LENGTH_SHORT).show()
                }else{
                    ApplicationClass.sharedPreferencesmanager.deleteJwt()
                    Toast.makeText(requireContext(),"회원탈퇴 성공", Toast.LENGTH_SHORT).show()
                    // 로그인 화면으로 넘어갈건가??
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    // 로그인 토큰도 지워야하나??
                }
            }
        }

        withdrawalno.setOnClickListener {
            dismiss()
        }

    }
}