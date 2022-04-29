package com.example.helperstartup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helperstartup.databinding.ActivityMainBinding
import com.example.helperstartup.model.UserModel
import com.example.helperstartup.model.UserPreferenceShared
import com.example.helperstartup.view.activity.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mUserPreference: UserPreferenceShared
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserPreference = UserPreferenceShared(this)
        showExistingPreference()
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun deleteUser() {
        mUserPreference.setUser(UserModel("", "", "", false))
    }
}