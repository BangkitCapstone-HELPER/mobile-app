package com.example.helperstartup.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import com.example.helperstartup.R
import com.example.helperstartup.databinding.ActivityLoginBinding
import com.example.helperstartup.model.UserModel
import com.example.helperstartup.model.UserPreferenceShared

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: UserModel

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
            val email = binding.emailEditText.text
            val password = binding.passwordEditText.text

        }

//        binding.signupPage.setOnClickListener {
//            val intent =
//                Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun validateEmptyField(edit: EditText): Boolean {
        if (edit.text.toString().isEmpty()) {
            edit.error = getString(R.string.empty_field)
            return false
        }
        return true
    }

    private fun saveUser(name: String, email: String, token: String) {
        val userPreference = UserPreferenceShared(this)
        user.name = name
        user.email = email
        user.token = token
        user.isLogin = true
        userPreference.setUser(user)
    }
}