package com.example.helperstartup.View.Catering.home

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.helperstartup.R
import com.example.helperstartup.View.Catering.home.ui.main.SectionsPagerAdapter
import com.example.helperstartup.databinding.ActivityMenuDetailBinding

class MenuDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMenuDetailBinding
    private lateinit var imageSlider : ImageSlider
    private var slideModel = ArrayList<SlideModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}