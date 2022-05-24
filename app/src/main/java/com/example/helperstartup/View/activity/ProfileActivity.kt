package com.example.helperstartup.View.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.R
import com.example.helperstartup.View.HandlingError.PageNotFound
import com.example.helperstartup.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity(){
    private lateinit var binding: ActivityProfileBinding
    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User

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

    private fun setupView(){
        if ( !userModel.image.isEmpty()) {
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
        if (userModel.email.isEmpty()){
            binding.emailTextView.text = "Email belum terdaftar"
        }
        if(userModel.phoneNumber.isEmpty()){
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
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.changePasswordButton.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteUser() {
        mUserPreference.setUser(User("", "", "", false, "", ""))
    }
}