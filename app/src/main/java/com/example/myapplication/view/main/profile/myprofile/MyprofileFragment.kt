package com.example.myapplication.view.main.profile.myprofile

import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMyprofileBinding
import com.example.myapplication.data.remote.model.UserProfileEditDto
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.main.SecondActivity
import com.example.myapplication.viewmodel.UserViewModel
import com.example.myapplication.widget.utils.EventObserver
import com.example.myapplication.widget.utils.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyprofileFragment : BaseFragment<FragmentMyprofileBinding>(R.layout.fragment_myprofile) {

    private lateinit var userViewModel: UserViewModel

    private var defaultImage = R.drawable.icon_myprofile_profileimage
    private var user_name: String? = null
    private var user_email: String? = null
    private var user_image: String? = null

    override fun init() {
        userViewModel = (activity as SecondActivity).userViewModel
        binding.uservm = userViewModel

        // 이미지를 넣을 경우
        userViewModel.profile_photo.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Uri> {
                Glide.with(this)
                    .load(it) //이미지
                    .into(binding.myprofileImagePhoto)

                userViewModel.user_image_data.value = it.toString()
            })

        // 이미지를 삭제했을 경우
        userViewModel.default_profile_photo.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer<Int> {
                Glide.with(this)
                    .load(it)
                    .into(binding.myprofileImagePhoto)

                userViewModel.user_image_data.value = it.toString()
            })

        hideBottomNavigation(true)
        initAppbar(binding.myprofileToolbar, "내 정보 수정", true, false)

        // 자신의 프로필 사진을 눌렀을 경우
        userViewModel.fix_bt_event.observe(this@MyprofileFragment, EventObserver {
            val bottomSheetDialogFragment: BottomSheetDialogFragment = PermissionFragment()
            bottomSheetDialogFragment.show(parentFragmentManager, null)
        })

        // 저장 버튼을 눌렀을 경우
        userViewModel.save_bt_event.observe(this@MyprofileFragment, EventObserver {
            if(userViewModel.user_data_change_button.value == true){
                userViewModel.set_user_profile_data(
                    UserProfileEditDto(
                        userViewModel.user_email_data.value.toString(),
                        userViewModel.user_image_data.value.toString(),
                        userViewModel.user_name_data.value.toString()
                    )
                )
                Toast.makeText(requireContext(), "프로필 정보가 편집되었습니다.", Toast.LENGTH_SHORT).show()
            }else{

            }
        })

        // 수정 후 프로필 정보 재요청
        userViewModel.user_profile_edit_result_data.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Loading->{

                }
                is NetworkResult.Error->{

                }
                is NetworkResult.Success->{
                    if(it.data!!.success){
                        userViewModel.get_user_profile_data()
                    }
                }
            }
        })

        // 초기 유저 정보
        userViewModel.user_profile_data.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Glide.with(this)
                        .load(defaultImage)
                        .into(binding.myprofileImagePhoto)
                }

                is NetworkResult.Success -> {
                    user_name = it.data!!.data!!.name
                    user_email = it.data.data!!.email
                    user_image = it.data.data!!.image
                    userViewModel.user_name_data.value = user_name
                    userViewModel.user_email_data.value = user_email
                    userViewModel.user_image_data.value = user_image
                    Log.d("whatisthis",user_image.toString())
                    Glide.with(this@MyprofileFragment)
                        .load(user_image)
                        .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
                        .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
                        .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                        .into(binding.myprofileImagePhoto)
                }
            }
        })

        userViewModel.user_name_data.observe(this, Observer {
            userViewModel.user_data_change.value = (user_name != null && user_name != it && !it.isEmpty())
        })

        userViewModel.user_email_data.observe(this, Observer {
            userViewModel.user_data_change.value = (user_email !=null && user_email!= it && !it.isEmpty())

        })

        userViewModel.user_image_data.observe(this, Observer {
            userViewModel.user_data_change.value = user_image !=null && user_image!= it.toString()

        })

        userViewModel.user_data_change.observe(this, Observer {
            userViewModel.user_data_change_button.value = it

            button_activate(userViewModel.user_data_change_button.value!!)
        })
    }

    fun button_activate(switch:Boolean){
        if(switch){
            binding.myprofileTextviewSavebutton.setBackgroundResource(R.drawable.logout_withdrawal_button_background2)
            binding.myprofileTextviewSavebutton.setTextColor(Color.WHITE)
        }else{
            binding.myprofileTextviewSavebutton.setBackgroundResource(R.drawable.gray_button_background)
            binding.myprofileTextviewSavebutton.setTextColor(Color.parseColor("#A4A4A4"))
        }
    }

}