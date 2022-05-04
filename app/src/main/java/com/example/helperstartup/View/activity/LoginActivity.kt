package com.example.helperstartup.View.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.LoginApiResponse
import com.example.helperstartup.Model.Service.ResponseApi.UserInfo
import com.example.helperstartup.R
import com.example.helperstartup.View.Dashboard.Dashboard
import com.example.helperstartup.databinding.ActivityLoginBinding
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mUserPreference: UserPreference
    private var user: User = User("", "","", false)
    private val service = ApiConfig.getApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserPreference = UserPreference(this)
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
        binding.loginButton.setOnClickListener {
            val emailEditText = binding.emailEditText
            val passwordEditText = binding.passwordEditText

            if (validateEmptyField(emailEditText) && validateEmptyField(passwordEditText) && Patterns.EMAIL_ADDRESS.matcher(
                    emailEditText.text.toString()
                ).matches()
            ) {
                showLoading(true)
                service.login(
                    UserInfo(email = emailEditText.text.toString(), password = passwordEditText.text.toString())
                )
                    .enqueue(object : Callback<LoginApiResponse> {
                        override fun onResponse(
                            call: Call<LoginApiResponse>,
                            response: Response<LoginApiResponse>
                        ) {
                            when {
                                response.isSuccessful -> {
                                    showLoading(false)
                                    val responseBody = response.body()
                                    saveUser(
                                        responseBody?.data?.user?.name.toString(),
                                        responseBody?.data?.user?.email.toString(),
                                        responseBody?.data?.token.toString()
                                    )

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Selamat datang " +  responseBody?.data?.user?.name.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent =
                                        Intent(this@LoginActivity, Dashboard::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                response.code() == 400 -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Email atau password salah!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                response.code() == 403 -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Unauthorized!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                response.code() == 404 -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Email atau password salah!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                response.code() == 422 -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Format masukan salah",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<LoginApiResponse>, t: Throwable) {
                            showLoading(false)
                            Toast.makeText(
                                this@LoginActivity,
                                "Terjadi kesalahan pada server",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })


            }
        }
    }


    private fun validateEmptyField(edit: EditText): Boolean {
        if (edit.text.toString().isEmpty()) {
            edit.error = getString(R.string.empty_field)
            return false
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun saveUser(name: String, email: String, token: String) {
        val userPreference = UserPreference(this)
        user.name = name
        user.email = email
        user.token = token
        user.isLogin = true
        userPreference.setUser(user)
    }
}