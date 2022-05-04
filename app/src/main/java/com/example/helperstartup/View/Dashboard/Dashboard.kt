package com.example.helperstartup.View.Dashboard

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.example.helperstartup.R
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.View.activity.LoginActivity

class Dashboard : AppCompatActivity() {
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mUserPreference = UserPreference(this)
        setupView()
        showExistingPreference()
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

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun deleteUser() {
        mUserPreference.setUser(User("", "", "", false))
    }
}