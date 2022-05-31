package com.example.helperstartup.View.handlingError

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.helperstartup.R

class PageNotFound : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_not_found)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}