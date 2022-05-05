package com.example.helperstartup.View.Catering.Menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.helperstartup.R
import com.example.helperstartup.View.Catering.home.HomeFragment
import com.example.helperstartup.View.Catering.keranjang.KeranjangFragment
import com.example.helperstartup.View.Catering.profile.ProfileFragment
import com.example.helperstartup.View.Catering.riwayat.RiwayatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuCateringActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_catering)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        val fragment_home= HomeFragment()
        val fragment_keranjang= KeranjangFragment()
        val fragment_riwayat= RiwayatFragment()
        val fragment_profile= ProfileFragment()

        if (savedInstanceState == null) {
            setCurrentFragment(fragment_home)
        }
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> setCurrentFragment(fragment_home)
                R.id.miKeranjang -> setCurrentFragment(fragment_keranjang)
                R.id.miRiwayat -> setCurrentFragment(fragment_riwayat)
                R.id.miProfile -> setCurrentFragment(fragment_profile)

            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}