package com.example.helperstartup.View.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.ChangePasswordResponse
import com.example.helperstartup.Model.Service.request.PasswordChange
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.databinding.ActivityChangePasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showExistingPreference()
        setupActionBar()
        passwordFocusListener()

        binding.saveButton.setOnClickListener {
            submit()
        }
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
        supportActionBar?.title = "Ganti Password"
    }

    private fun submit() {
        val validOldPassword = binding.oldPasswordEditTextLayout.helperText == null && !binding.oldPasswordEditText.text.toString().isEmpty()
        val validNewPassword = binding.newPasswordEditTextLayout.helperText == null && !binding.newPasswordEditText.text.toString().isEmpty()
        if (validOldPassword && validNewPassword) {
            val client = ApiConfig.getApiService().changePassword(
                auth = "bearer " + userModel.token,
                passwordData = PasswordChange(
                    binding.newPasswordEditText.text.toString(),
                    binding.oldPasswordEditText.text.toString(),
                )
            )
            showLoading(true)
            client.enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    if (response.isSuccessful) {
                        showLoading(false)
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            "Berhasil mengubah password",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@ChangePasswordActivity, ProfileActivity::class.java))
                        finish()
                    } else {
                        showLoading(false)
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            "Password lama / baru tidak sesuai",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    showLoading(false)
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Terjadi kesalahan pada jaringan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Toast.makeText(this, "Periksa kembali masukan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun passwordFocusListener() {
        binding.oldPasswordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.oldPasswordEditTextLayout.helperText = validOldPassword()
            }
        }

        binding.newPasswordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.newPasswordEditTextLayout.helperText = validNewPassword()
            }
        }
    }

    private fun validOldPassword(): String? {
        val oldPasswordText = binding.oldPasswordEditText.text.toString()
        if (oldPasswordText.length < 8) {
            return "Minimal 8 karakter"
        }
        return null
    }

    private fun validNewPassword(): String? {
        val newPasswordText = binding.newPasswordEditText.text.toString()
        if (newPasswordText.length < 8) {
            return "Minimal 8 karakter"
        }
        return null
    }
}