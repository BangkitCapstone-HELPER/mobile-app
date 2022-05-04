package com.example.helperstartup.View.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.RegisterResponse
import com.example.helperstartup.Model.Service.request.UserRegister
import com.example.helperstartup.R
import com.example.helperstartup.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private val service = ApiConfig.getApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginPage2.setOnClickListener {
            val intent =
                Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.submitButton.setOnClickListener {
            if (validateRegisterField()) {
                showLoading(true)
                service.register(
                    UserRegister(
                        email = emailEditText.text.toString(),
                        password = passwordEditText.text.toString(),
                        name = nameEditText.text.toString(),
                        address = "Default address",
                        phoneNumber = phoneNumberEditText.text.toString()
                    )
                ).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        when {
                            response.isSuccessful -> {
                                showLoading(false)
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Pendaftaran berhasil",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            response.code() == 400 -> {
                                showLoading(false)
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Email sudah digunakan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                showLoading(false)
                                Toast.makeText(
                                    this@RegisterActivity,
                                    response.body()?.message ?: "Masukan tidak valid",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        showLoading(false)
                        Toast.makeText(
                            this@RegisterActivity,
                            "Terjadi kesalahan pada server / jaringan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }

    private fun bind() {
        nameEditText = binding.nameEditText
        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText
        phoneNumberEditText = binding.phoneNumberEditText
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun validateEmptyFields(editList: ArrayList<EditText>): Boolean {
        editList.forEach {
            if (it.text.toString().isEmpty()) {
                it.error = getString(R.string.empty_field)
                return false
            }
        }
        return true
    }

    private fun validateEmailField(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString()).matches()
    }

    private fun validatePasswordField(): Boolean {
        if (passwordEditText.text.toString().length < 8) {
            passwordEditText.error = getString(R.string.min_chars_password)
            return false
        }
        return true
    }

    private fun validateRegisterField(): Boolean {
        return (validateEmptyFields(
            arrayListOf(
                nameEditText,
                emailEditText,
                passwordEditText,
                phoneNumberEditText
            )
        ) && validateEmailField() && validatePasswordField())
    }
}