package com.example.helperstartup.View.Catering.home


import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.helperstartup.Model.Service.ResponseApi.DataItem
import com.example.helperstartup.R
import com.example.helperstartup.View.Adapter.TabLayoutAdapter
import com.google.android.material.tabs.TabLayout


class DetailMenu : AppCompatActivity() {
    private lateinit var imageSlider : ImageSlider
    private var slideModel = ArrayList<SlideModel>()
    private lateinit var priceText : TextView
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    private lateinit var data : DataItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        data = intent.getParcelableExtra("DATA")!!

        Log.i("Datanya gimana", data.toString())

        imageSlider = findViewById(R.id.imageSlider)

        data?.dayMenus?.map { i ->
            slideModel.add(SlideModel(i?.image.toString()))
        }
        imageSlider.setImageList(slideModel, true)

        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Senin"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Selasa"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Rabu"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Kamis"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Jumat"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Sabtu"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Minggu"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = TabLayoutAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })


    }

    fun sendDataMenu(): DataItem {
        return data
    }
}