package com.example.helperstartup.View.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.helperstartup.Model.service.ApiConfig
import com.example.helperstartup.Model.service.ResponseApi.ChangeProfileResponse
import com.example.helperstartup.Model.service.request.ProfileChange
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.databinding.ActivityUpdateProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User
    private lateinit var binding: ActivityUpdateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        showExistingPreference()
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

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Perbarui Profil"
    }

    private fun setupView() {
        binding.phoneEditText.setText(userModel.phoneNumber)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupAction() {
        binding.saveButton.setOnClickListener {
            submit()
        }
    }

    private fun submit() {
        val newPhoneNumberValid = binding.phoneEditText.text.toString().length >= 8
        if (newPhoneNumberValid) {
            val client = ApiConfig.getApiService().changeProfile(
                auth = "bearer " + userModel.token,
                data = ProfileChange(binding.phoneEditText.text.toString())
            )
            showLoading(true)
            client.enqueue(object : Callback<ChangeProfileResponse> {
                override fun onResponse(
                    call: Call<ChangeProfileResponse>,
                    response: Response<ChangeProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        showLoading(false)
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            "Berhasil mengubah password",
                            Toast.LENGTH_SHORT
                        ).show()
                        response.body()?.data?.phoneNumber?.let { mUserPreference.updatePhone(it) }
                        userModel = mUserPreference.getUser()
                        startActivity(
                            Intent(
                                this@UpdateProfileActivity,
                                ProfileActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        showLoading(false)
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            "Terjadi kesalahan silakan coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ChangeProfileResponse>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(
                        this@UpdateProfileActivity,
                        "Terjadi kesalahan pada jaringan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            binding.phoneEditText.error = "Minimal 8 karakter"
        }
    }
}