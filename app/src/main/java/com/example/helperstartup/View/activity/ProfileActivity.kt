package com.example.helperstartup.View.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.Model.reduceFileImage
import com.example.helperstartup.Model.service.ApiConfig
import com.example.helperstartup.Model.service.ResponseApi.ChangeProfileResponse
import com.example.helperstartup.Model.service.ResponseApi.UploadFileToStorageResponse
import com.example.helperstartup.Model.service.request.ImageProfileChange
import com.example.helperstartup.Model.service.request.ProfileChange
import com.example.helperstartup.Model.uriToFile
import com.example.helperstartup.R
import com.example.helperstartup.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showExistingPreference()
        setupActionBar()
        setupView()
        setupAction()
    }

    private fun showExistingPreference() {
        mUserPreference = UserPreference(this)
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupView() {
        if (!userModel.image.isEmpty()) {
            Picasso.get().load(userModel.image)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .resize(150, 150)
                .centerCrop()
                .into(binding.profileImage)
        } else {
            binding.profileImage.setImageResource(R.drawable.img_placeholder)
        }
        binding.nameProfile.text = userModel.name
        binding.emailTextView.text = userModel.email
        binding.phoneNumberTextView.text = userModel.phoneNumber
        if (userModel.email.isEmpty()) {
            binding.emailTextView.text = "Email belum terdaftar"
        }
        if (userModel.phoneNumber.isEmpty()) {
            binding.phoneNumberTextView.text = "Nomor telepon belum terdaftar"
        }

    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profil"
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            deleteUser()
            finishAffinity()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.updateProfileButton.setOnClickListener {
            val intent = Intent(this, UpdateProfileActivity::class.java)
            startActivity(intent)
        }

        binding.changePasswordButton.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.tvEditPhoto.setOnClickListener {
            startGallery()
        }
    }

    private fun deleteUser() {
        mUserPreference.setUser(User("", "", "", false, "", "", 0))
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih gambar")
        launcherIntentGallery.launch(chooser)
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ProfileActivity)
            getFile = myFile
            uploadImage()
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val folder =
                "profile/${userModel.id}".toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )

            val client = ApiConfig.getApiService().uploadFile(imageMultipart, folder)

            client.enqueue(object : Callback<UploadFileToStorageResponse> {
                override fun onResponse(
                    call: Call<UploadFileToStorageResponse>,
                    response: Response<UploadFileToStorageResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val url = responseBody.data.url
                            patchProfileImage(url)

                        }
                    } else {
                        Toast.makeText(
                            this@ProfileActivity,
                            response.message().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UploadFileToStorageResponse>, t: Throwable) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Gagal mengunggah gambar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        } else {
            Toast.makeText(
                this@ProfileActivity,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun patchProfileImage(url: String) {
        val client = ApiConfig.getApiService().changeImageProfile(
            auth = "bearer " + userModel.token,
            data = ImageProfileChange(url)
        )
        client.enqueue(object : Callback<ChangeProfileResponse> {
            override fun onResponse(
                call: Call<ChangeProfileResponse>,
                response: Response<ChangeProfileResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Berhasil mengubah gambar profil",
                        Toast.LENGTH_SHORT
                    ).show()
                    response.body()?.data?.image?.let { mUserPreference.updateImage(it) }
                    userModel = mUserPreference.getUser()
                    Picasso.get().load(userModel.image)
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_placeholder)
                        .resize(150, 150)
                        .centerCrop()
                        .into(binding.profileImage)
                } else {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Terjadi kesalahan silakan coba lagi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ChangeProfileResponse>, t: Throwable) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Terjadi kesalahan pada jaringan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}