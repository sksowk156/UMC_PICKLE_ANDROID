package com.example.myapplication.ui.main.profile.myprofile

import android.Manifest.permission.*
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMyprofileBinding
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.ui.main.profile.logout.LogoutFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class MyprofileFragment() : BaseFragment<FragmentMyprofileBinding>(R.layout.fragment_myprofile) {

    private var CAMERA_PERMISSION: Boolean = false
    private var WRITE_EXTERNAL_STORAGE_PERMISSION: Boolean = false
    private var READ_EXTERNAL_STORAGE_PERMISSION: Boolean = false

    override fun init() {
        hideBottomNavigation(true)
        initAppbar(binding.myprofileToolbar, "내 정보 수정", true, false)
        binding.myprofileImagePhoto.setOnClickListener {
//            openDialog(requireContext())
            val bottomSheetDialogFragment: BottomSheetDialogFragment = PermissionFragment()
            bottomSheetDialogFragment.show(parentFragmentManager, null)
        }
    }

    private fun openDialog(context: Context) {
        val dialogLayout = layoutInflater.inflate(R.layout.dialog, null)
        val dialogBuild = AlertDialog.Builder(context).apply {
            setView(dialogLayout)
        }
        val dialog = dialogBuild.create().apply { show() }

        val cameraAddBtn = dialogLayout.findViewById<Button>(R.id.dialog_btn_camera)
        val fileAddBtn = dialogLayout.findViewById<AppCompatButton>(R.id.dialog_btn_file)

        // 카메라 버튼 클릭 시
        cameraAddBtn.setOnClickListener {
            if (checkPermission(arrayOf(CAMERA))) { // 카메라 실행
                camera()
//                // 2. TakePicturePreview
//                getTakePicturePreview.launch(null)    // Bitmap get
            } else { // 권한 요청
                requestMultiplePermission.launch(arrayOf(CAMERA))
            }
            dialog.dismiss()
        }

        // 갤러리 버튼 클릭 시
        fileAddBtn.setOnClickListener {
            if (checkPermission(arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE))) { // 갤러리 실행
                gallery()
            } else { // 권한 요청
                requestMultiplePermission.launch(
                    arrayOf(
                        WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE
                    )
                )
            }
            dialog.dismiss()
        }
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
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                if (!it.value) {
                    showToast("권한 허용 필요")
                } else {
                    if (it.key == CAMERA) {
                        CAMERA_PERMISSION = true // 카메라 권한 허용
                    } else if (it.key == WRITE_EXTERNAL_STORAGE) {
                        WRITE_EXTERNAL_STORAGE_PERMISSION = true // 갤러리 쓰기 허용
                    } else if (it.key == READ_EXTERNAL_STORAGE) {
                        READ_EXTERNAL_STORAGE_PERMISSION = true // 갤러리 읽기 허용
                    }
                }

                if (CAMERA_PERMISSION) { // 권한 허용이후에 바로 실행하기 위함, 권한 체크는 한개씩 하기 때문에 이렇게 설정
                    camera() // 카메라 실행
                } else if (WRITE_EXTERNAL_STORAGE_PERMISSION && READ_EXTERNAL_STORAGE_PERMISSION) {
                    gallery()  // 갤러리 실행
                }

            }
        }

    // 갤러리에서 받아온 이미지를 프로필 사진으로 설정
    private val getContentImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            //결과 코드 OK , 결가값 null 아니면
            if (it.resultCode == RESULT_OK && it.data != null) {
                //값 담기
                val uri = it.data!!.data
                //화면에 보여주기
                Glide.with(this)
                    .load(uri) //이미지
                    .into(binding.myprofileImagePhoto) //보여줄 위치
            }
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

    // 카메라를 실행한 후 찍은 사진을 프로필 사진으로 설정
    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            pictureUri.let { binding.myprofileImagePhoto.setImageURI(pictureUri) }
        }
    }

    // 카메라 실행
    private fun camera() {
        pictureUri = createImageFile()
        getTakePicture.launch(pictureUri)
    }

//    // 카메라를 실행하며 결과로 비트맵 이미지를 얻음
//    private val getTakePicturePreview =
//        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
//            bitmap.let { binding.myprofileImagePhoto.setImageBitmap(bitmap) }
//        }

    // 카메라로 찍은 사진을 갤러리에 저장하기 위한 메소드(파일명 설정 등등)
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

//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        //버튼 이벤트
//        binding.galleryBtn.setOnClickListener {
//
//            //갤러리 호출
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.setDataAndType(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                "image/*"
//            )
//            activityResult.launch(intent)
//        }
//    }//onCreate
//
//    //결과 가져오기
//    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//
//        //결과 코드 OK , 결가값 null 아니면
//        if (it.resultCode == RESULT_OK && it.data != null) {
//            //값 담기
//            val uri = it.data!!.data
//
//            //화면에 보여주기
//            Glide.with(this)
//                .load(uri) //이미지
//                .into(binding.imageView) //보여줄 위치
//        }
//    }
}