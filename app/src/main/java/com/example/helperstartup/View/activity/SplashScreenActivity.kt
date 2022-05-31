package com.example.helperstartup.View.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.View.dashboard.Dashboard

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showExistingPreference()
    }

    private fun showExistingPreference() {
        mUserPreference = UserPreference(this)
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, Dashboard::class.java))
            finish()
        }
    }
}