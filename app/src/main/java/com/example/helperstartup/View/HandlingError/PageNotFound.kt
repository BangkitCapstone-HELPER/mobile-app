package com.example.helperstartup.View.HandlingError

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import com.example.helperstartup.R
import com.example.helperstartup.View.Dashboard.Dashboard

class PageNotFound : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_not_found)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}