package com.example.helperstartup.View.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helperstartup.Model.Service.ApiConfig
import com.example.helperstartup.Model.Service.ResponseApi.ResponseMenu
import com.example.helperstartup.Model.Service.ResponseApi.ResponseUploadScanner
import com.example.helperstartup.R
import com.example.helperstartup.View.Adapter.MenuAdapter
import com.example.helperstartup.View.Adapter.ScanningAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultScanning : AppCompatActivity() {

    private lateinit var listMenu : RecyclerView
    private lateinit var dataMenu : ResponseUploadScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_scanning)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dataMenu = intent.getParcelableExtra<ResponseUploadScanner>("message")!!

        Log.i("datamenu", dataMenu.message.toString())

        listMenu = findViewById(R.id.scanning_rv)

        listMenu.layoutManager = LinearLayoutManager(this@ResultScanning)
        val listMenuAdapter = ScanningAdapter(dataMenu)
        listMenu.adapter = listMenuAdapter
    }
}