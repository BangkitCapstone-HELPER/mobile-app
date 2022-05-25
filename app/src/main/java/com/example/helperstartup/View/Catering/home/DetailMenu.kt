package com.example.helperstartup.View.Catering.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.helperstartup.Model.Service.ResponseApi.DataItem
import com.example.helperstartup.R

class DetailMenu : AppCompatActivity() {
    private lateinit var imageSlider : ImageSlider
    private var slideModel = ArrayList<SlideModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        val data = intent.getParcelableExtra<DataItem>("DATA")

        imageSlider = findViewById(R.id.imageSlider)

        data?.dayMenus?.map { i ->
            slideModel.add(SlideModel(i?.image.toString()))
        }
        imageSlider.setImageList(slideModel, true)
    }
}