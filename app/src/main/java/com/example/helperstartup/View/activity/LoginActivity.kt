package com.example.helperstartup.View.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import com.example.helperstartup.R
import com.example.helperstartup.View.Dashboard.Dashboard
import com.example.helperstartup.databinding.ActivityLoginBinding
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            if (validateEmptyField(emailEditText) && validateEmptyField(passwordEditText)) {
                val intent =
                    Intent(this, Dashboard::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
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

private fun saveUser(name: String, email: String, token: String) {
    val userPreference = UserPreference(this)
    user.name = name
    user.email = email
    user.token = token
    user.isLogin = true
    userPreference.setUser(user)
}
}