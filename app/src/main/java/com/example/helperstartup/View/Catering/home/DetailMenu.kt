package com.example.helperstartup.View.Catering.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.helperstartup.R

class DetailMenu : AppCompatActivity() {
    private lateinit var imageSlider : ImageSlider
    private var slideModel = ArrayList<SlideModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        imageSlider = findViewById(R.id.imageSlider)
        slideModel.add(SlideModel("https://picsum.photos/200/300"))
        slideModel.add(SlideModel("https://picsum.photos/200/300"))
        slideModel.add(SlideModel("https://picsum.photos/200/300"))
        slideModel.add(SlideModel("https://picsum.photos/200/300"))
        imageSlider.setImageList(slideModel, true)
    }
}