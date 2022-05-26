package com.example.helperstartup.View.Catering.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helperstartup.Model.User
import com.example.helperstartup.Model.UserPreference
import com.example.helperstartup.R
import com.example.helperstartup.View.Catering.camera.CameraFragment
import com.example.helperstartup.View.Catering.home.HomeFragment
import com.example.helperstartup.View.Catering.riwayat.RiwayatFragment
import com.example.helperstartup.View.activity.LoginActivity
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MenuCateringActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: ChipNavigationBar

    private lateinit var mUserPreference: UserPreference
    private lateinit var userModel: User
    private val fragmentHome = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_catering)
        mUserPreference = UserPreference(this)
        showExistingPreference()
        bottomNavigationView = findViewById(R.id.bottom_nav_bar)

        setupActionBar()
        openMainFragment()

        bottomNavigationView.setItemSelected(R.id.miHome)

        bottomNavigationView.setOnItemSelectedListener {
            when (it) {

                R.id.miHome -> {
                    openMainFragment()
                }

                R.id.miScanning -> {
                    val scanningFragment = CameraFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, scanningFragment).commit()

                }
                R.id.miRiwayat -> {
                    val riwayatFragment = RiwayatFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flFragment, riwayatFragment).commit()
                }
            }
        }

        // if action after new order
        getDataFromOrderConfirmation()
    }

    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()
        if (!userModel.isLogin) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Catering"
    }

    private fun openMainFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragmentHome)
        transaction.commit()
    }

    private fun getDataFromOrderConfirmation() {
        val data = intent.extras?.getBoolean("isSuccess")
        if (data != null) {
            bottomNavigationView.setItemSelected(R.id.miRiwayat)
            val mFragment = RiwayatFragment()
            val mBundle = Bundle()
            mBundle.putBoolean("isSuccess", data)
            mFragment.arguments = mBundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, mFragment).commit()
        }

    }
}