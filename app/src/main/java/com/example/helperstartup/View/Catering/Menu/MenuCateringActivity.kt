package com.example.helperstartup.View.Catering.Menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.helperstartup.R
import com.example.helperstartup.View.Catering.camera.CameraFragment
import com.example.helperstartup.View.Catering.home.HomeFragment
import com.example.helperstartup.View.Catering.riwayat.RiwayatFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MenuCateringActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView : ChipNavigationBar
    val fragment_home= HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_catering)

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
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Catering"
    }

    private fun openMainFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment_home)
        transaction.commit()
    }
}