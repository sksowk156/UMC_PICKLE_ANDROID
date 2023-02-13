package com.example.myapplication.ui.main.profile.myprofile

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_permission.*
import java.text.SimpleDateFormat
import java.util.*

class PermissionFragment() : BottomSheetDialogFragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var CAMERA_PERMISSION: Boolean = false
    private var WRITE_EXTERNAL_STORAGE_PERMISSION: Boolean = false
    private var READ_EXTERNAL_STORAGE_PERMISSION: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission, container, false)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.AppBottomSheetDialogTheme3)

        return super.onCreateDialog(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        var permissioncamera_photo = view.findViewById<Button>(R.id.permission_camera_photo)
        var permissiondeletephoto = view.findViewById<Button>(R.id.permission_deletephoto)
        var permissioncancel = view.findViewById<Button>(R.id.permission_cancel)

        permission_taking_picture.setOnClickListener{
            if (checkPermission(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE))) { // 갤러리 실행
                    camera()
            }else{
                requestMultiplePermission.launch(arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        }



        // 사진이나 앨범에서 선택
        permissioncamera_photo.setOnClickListener {
            // 갤러리 버튼 클릭 시
            if (checkPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))) { // 갤러리 실행
                gallery()
            } else { // 권한 요청
                requestMultiplePermission.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        }


        // 이미지 지우기
        permissiondeletephoto.setOnClickListener {


            profileViewModel.set_default_photo((R.drawable.img_1))
            dismiss()

        }


        // 그냥 취소
        permissioncancel.setOnClickListener {
            dismiss()
        }

    }


    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            profileViewModel.set_profile_photo(pictureUri!!)
        }
        dismiss()
    }



    // 카메라를 실행한 후 찍은 사진을 프로필 사진으로 설정
    private fun camera() {
        pictureUri = createImageFile()
        getTakePicture.launch(pictureUri)
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            content
        )
    }



    // 권한 체크
    fun checkPermission(permissions: Array<String>): Boolean {
        var result: Boolean = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // api 23 부터
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) { // 권한 허용o
                    result = true
                }
                // 권한 허용x -> 요청
            }
        }
        return result
    }

    // 권한 요청
    private val requestMultiplePermission =
        super.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                if (!it.value) {
                    Toast.makeText(requireContext(), "권한 허용 필요", Toast.LENGTH_SHORT).show()
                } else {
                    if (it.key == Manifest.permission.CAMERA) {
                        CAMERA_PERMISSION = true // 카메라 권한 허용
                    } else if (it.key == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                        WRITE_EXTERNAL_STORAGE_PERMISSION = true // 갤러리 쓰기 허용
                    } else if (it.key == Manifest.permission.READ_EXTERNAL_STORAGE) {
                        READ_EXTERNAL_STORAGE_PERMISSION = true // 갤러리 읽기 허용
                    }
                }

                if (WRITE_EXTERNAL_STORAGE_PERMISSION && READ_EXTERNAL_STORAGE_PERMISSION) {
                    gallery()  // 갤러리 실행
                }

            }
        }



    // 갤러리에서 받아온 이미지를 프로필 사진으로 설정
    private val getContentImage =
        super.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            //결과 코드 OK , 결가값 null 아니면
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                //값 담기
                val uri = it.data!!.data
                profileViewModel.set_profile_photo(uri!!)
            }
            dismiss()
        }

    // 갤러리 실행
    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        getContentImage.launch(intent)
    }


}