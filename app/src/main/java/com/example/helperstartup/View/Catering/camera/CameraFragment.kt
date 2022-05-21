package com.example.helperstartup.View.Catering.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.FileUploadResponse
import com.example.helperstartup.Model.createCustomTempFile
import com.example.helperstartup.Model.reduceFileImage
import com.example.helperstartup.Model.uriToFile
import com.example.helperstartup.View.Dashboard.Dashboard
import com.example.helperstartup.View.camera.ResultScanning
import com.example.helperstartup.databinding.FragmentCameraBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var currentPhotoPath: String

    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progresbar.visibility = View.GONE

        if (!allPermissionsGranted()) {
            requestPermissions(
                CameraFragment.REQUIRED_PERMISSIONS,
                CameraFragment.REQUEST_CODE_PERMISSIONS
            )
        }

        binding.uploadPhotoButton.setOnClickListener { startTakePhoto() }
        binding.retakePhoto.setOnClickListener { startTakePhoto() }
        binding.uploadPhotoButton3.setOnClickListener { uploadImage() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CameraFragment.REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    context,
                    "Permission Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = CameraFragment.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(),it) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireContext().applicationContext).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.helperstartup",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = BitmapFactory.decodeFile(getFile?.path)
            binding.previewImageView.setImageBitmap(result)
            binding.textView2.setVisibility(View.GONE)
            binding.imageView2.setVisibility(View.GONE)
            binding.uploadPhotoButton.setVisibility(View.GONE)
            binding.previewImageView.setVisibility(View.VISIBLE)
            binding.uploadPhotoButton3.setVisibility(View.VISIBLE)
            binding.retakePhoto.setVisibility(View.VISIBLE)
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val client = ApiConfig.getApiService().uploadImage(
                imageMultipart,
                "Bearer "
            )
            binding.progresbar.visibility = View.VISIBLE
            client.enqueue(object : Callback<FileUploadResponse> {
                override fun onResponse(
                    call: Call<FileUploadResponse>,
                    response: Response<FileUploadResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                            binding.progresbar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent =
                                Intent(context, Dashboard::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    } else {
                        binding.progresbar.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                    binding.progresbar.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
        else {
            Toast.makeText(
                context,
                "getString(R.string.uploadfoto)",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(context, ResultScanning::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val CAMERA_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}