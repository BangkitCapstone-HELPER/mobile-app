package com.example.helperstartup.View.HandlingError

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.helperstartup.R
import com.example.helperstartup.View.Dashboard.Dashboard

class PageNotFound : AppCompatActivity() {

    private lateinit var buttonBack : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_not_found)

        buttonBack.setOnClickListener{
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }
}