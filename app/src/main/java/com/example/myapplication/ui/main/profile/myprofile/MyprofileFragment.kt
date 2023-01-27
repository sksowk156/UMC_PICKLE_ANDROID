package com.example.myapplication.ui.main.profile.myprofile

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMyprofileBinding
import com.example.myapplication.ui.main.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class MyprofileFragment : BaseFragment<FragmentMyprofileBinding>(R.layout.fragment_myprofile) {

    override fun init() {
        hideBottomNavigation(true)
        binding.myprofileImagePhoto.setOnClickListener {
            openDialog(requireContext())
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
        requestMultiplePermission.launch(arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA))

        cameraAddBtn.setOnClickListener {
            // 1. TakePicture
            pictureUri = createImageFile()
            getTakePicture.launch(pictureUri)   // Require Uri

            // 2. TakePicturePreview
//            getTakePicturePreview.launch(null)    // Bitmap get

            dialog.dismiss()
        }
        fileAddBtn.setOnClickListener {
            getContentImage.launch("image/*")
            dialog.dismiss()
        }
    }

    // 권한을 허용하도록 요청
    private val requestMultiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) {
                showToast("권한 허용 필요")
            }
        }
    }

    // 파일 불러오기
    private val getContentImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri.let { binding.myprofileImagePhoto.setImageURI(uri) }
    }

    // 카메라를 실행한 후 찍은 사진을 저장
    var pictureUri: Uri? = null
    private val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if(it) {
            pictureUri.let { binding.myprofileImagePhoto.setImageURI(pictureUri) }
        }
    }

    // 카메라를 실행하며 결과로 비트맵 이미지를 얻음
    private val getTakePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap.let { binding.myprofileImagePhoto.setImageBitmap(bitmap) }
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

}